package id.allana.kosakata.ui.kuiskata

import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import id.allana.kosakata.R
import id.allana.kosakata.base.arch.BaseFragment
import id.allana.kosakata.base.arch.GenericViewModelFactory
import id.allana.kosakata.data.local.database.WordDatabase
import id.allana.kosakata.data.local.datasource.WordDataSourceImpl
import id.allana.kosakata.databinding.FragmentKuisKataBinding

class KuisKataFragment :
    BaseFragment<FragmentKuisKataBinding, KuisKataViewModel>(FragmentKuisKataBinding::inflate),
    KuisKataContract.View {

    override fun initView() {
        getViewBinding().btnStart.setOnClickListener {
            intentToPermainanKuisKataFragment()
        }
    }

    override fun initViewModel(): KuisKataViewModel {
        val dataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireActivity()).wordDao())
        val repository = KuisKataRepository(dataSource)
        return GenericViewModelFactory(KuisKataViewModel(repository)).create(KuisKataViewModel::class.java)
    }

    override fun intentToPermainanKuisKataFragment() {
        val action =
            KuisKataFragmentDirections.actionNavigationKuisKataToPermainanKuisKataFragment()
        findNavController().navigate(action)
    }

    override fun getData() {
        getViewModel().getAllWords()
    }

    override fun observeData() {
        getViewModel().getAllWords().observe(viewLifecycleOwner) { list ->
            if (list.isEmpty()) {
                showError(true, getString(R.string.text_kuis_kata_empty))
                getViewBinding().btnStart.isEnabled = false
            } else {
                showError(false)
                getViewBinding().btnStart.isEnabled = true

            }
        }
    }

    override fun showError(isErrorEnabled: Boolean, msg: String?) {
        if (isErrorEnabled) msg?.let { Snackbar.make(getViewBinding().root, it, Snackbar.LENGTH_SHORT).show() }
    }
}