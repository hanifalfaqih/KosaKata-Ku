package id.allana.kosakata.ui.detailkosakata

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import id.allana.kosakata.R
import id.allana.kosakata.base.arch.BaseFragment
import id.allana.kosakata.base.arch.GenericViewModelFactory
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.database.WordDatabase
import id.allana.kosakata.data.local.datasource.WordDataSourceImpl
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.data.network.datasource.KosaKataDataSourceImpl
import id.allana.kosakata.databinding.FragmentDetailKosaKataBinding

class DetailKosaKataFragment : BaseFragment<FragmentDetailKosaKataBinding, DetailKosaKataViewModel>(
    FragmentDetailKosaKataBinding::inflate
), DetailKosaKataContract.View {

    private var word: Word? = null
    private var foreignWord: String? = null

    override fun initView() {
        setupMenu()
        getIntentData()
        setupDataToView()

        getViewBinding().btnBookmark.setOnClickListener {
            word?.let { currentWord ->
                currentWord.isFavorite = !currentWord.isFavorite
                val message =
                    if (currentWord.isFavorite) getString(R.string.text_add_to_favorite) else getString(
                        R.string.text_remove_from_favorite
                    )
                Snackbar.make(getViewBinding().root, message, Toast.LENGTH_SHORT).show()
                setBookmark(currentWord.isFavorite)
                getViewModel().updateWord(currentWord)
            }
        }

        getViewBinding().btnSoundText.setOnClickListener {
            if (checkInternetAvailable(requireContext())) {
                foreignWord?.let {
                    getViewModel().synthesizeTextToSpeech(it, requireContext())
                }
            } else {
                Snackbar.make(
                    getViewBinding().root,
                    getString(R.string.text_cant_connect_internet),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        //handleOnBackPressed()
    }

    override fun initViewModel(): DetailKosaKataViewModel {
        val wordDataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireContext()).wordDao())
        val kosaKataDataSource = KosaKataDataSourceImpl()
        val repository = DetailKosaKataRepository(wordDataSource, kosaKataDataSource)
        return GenericViewModelFactory(DetailKosaKataViewModel(repository)).create(
            DetailKosaKataViewModel::class.java
        )
    }

    override fun getIntentData() {
        val detailKosaKataArgs by navArgs<DetailKosaKataFragmentArgs>()
        word = detailKosaKataArgs.word
    }

    override fun setupDataToView() {
        word?.let { word ->
            getViewBinding().tvWordDetail.text = word.foreignWord
            getViewBinding().tvMeaningDetail.text = word.meaningWord
            setBookmark(word.isFavorite)
            foreignWord = word.foreignWord
        }
    }

    override fun setBookmark(isBookmark: Boolean) {
        val drawableId =
            if (isBookmark) R.drawable.ic_baseline_bookmark else R.drawable.ic_baseline_bookmark_border
        getViewBinding().btnBookmark.setImageResource(drawableId)
    }

    override fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val capabilities =
            connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
        return capabilities != null && (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) || capabilities.hasTransport(
            NetworkCapabilities.TRANSPORT_WIFI
        ))
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) Snackbar.make(getViewBinding().root, msg.toString(), Toast.LENGTH_SHORT)
            .show()
    }

    private fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.kosa_kata_detail_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.delete_kosa_kata -> {
                        showDeleteDialog()
                    }
                    android.R.id.home -> {
                        requireActivity().onBackPressedDispatcher.onBackPressed()
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }


    override fun showDeleteDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(getString(R.string.title_alert_dialog_delete))
            setMessage(getString(R.string.message_alert_dialog_delete))
                .setPositiveButton(getString(R.string.text_positive_button)) { _, _ ->
                    word?.let { getViewModel().deleteWord(it) }
                }
                .setNegativeButton(getString(R.string.text_negative_button)) { dialog, _ ->
                    dialog.dismiss()
                }
        }.create().show()
    }

    override fun observeData() {
        getViewModel().deleteWordLiveData().observe(this) { resource ->
            when (resource) {
                is Resource.Loading -> {}
                is Resource.Success -> {
                    Snackbar.make(getViewBinding().root, getString(R.string.text_delete_success), Toast.LENGTH_SHORT).show()
                    val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_activity_main) as NavHostFragment
                    val navController = navHostFragment.navController
                    navController.navigateUp()
                }
                is Resource.Error -> {
                    showError(true, getString(R.string.text_delete_failed))
                }
            }
        }
    }

    override fun checkInternetAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities != null &&
                (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI))
    }

}