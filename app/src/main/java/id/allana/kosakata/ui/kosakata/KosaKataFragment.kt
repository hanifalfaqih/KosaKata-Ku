package id.allana.kosakata.ui.kosakata

import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import id.allana.kosakata.R
import id.allana.kosakata.base.arch.BaseFragment
import id.allana.kosakata.base.arch.GenericViewModelFactory
import id.allana.kosakata.data.local.database.WordDatabase
import id.allana.kosakata.data.local.datasource.WordDataSourceImpl
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.databinding.FragmentKosaKataBinding
import id.allana.kosakata.ui.adapter.KosaKataAdapter
import id.allana.kosakata.ui.tambahkata.TambahKataDialogFragment

class KosaKataFragment :
    BaseFragment<FragmentKosaKataBinding, KosaKataViewModel>(FragmentKosaKataBinding::inflate),
    KosaKataContract.View, androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private lateinit var adapter: KosaKataAdapter

    override fun initView() {
        setupRecyclerView()
        getViewBinding().fabWord.setOnClickListener {
            TambahKataDialogFragment().show(childFragmentManager, TambahKataDialogFragment.TAG)
        }

        setupMenu()
    }

    override fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object: MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.kosa_kata_menu, menu)

                val search = menu.findItem(R.id.search_menu_kosa_kata)
                val searchView = search.actionView as androidx.appcompat.widget.SearchView
                searchView.isSubmitButtonEnabled = true
                searchView.setOnQueryTextListener(this@KosaKataFragment)

            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_a_z -> {
                        getViewModel().getAllWordsAZ().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                    R.id.menu_z_a -> {
                        getViewModel().getAllWordsZA().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                    R.id.menu_newest -> {
                        getViewModel().getAllWordsByNewest().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }

    override fun initViewModel(): KosaKataViewModel {
        val dataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireActivity()).wordDao())
        val repository = KosaKataRepository(dataSource)
        return GenericViewModelFactory(KosaKataViewModel(repository)).create(KosaKataViewModel::class.java)
    }

    override fun setupRecyclerView() {
        adapter = KosaKataAdapter()
        getViewBinding().rvListWords.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@KosaKataFragment.adapter
        }
    }

    override fun setListData(data: List<Word>) {
        adapter.submitList(data)
    }

    override fun searchKosaKata(query: String) {
        val searchQuery = "%$query%"
        getViewModel().searchKosaKata(searchQuery).observe(this) { list ->
            setListData(list)
            showContent(true)
        }
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvListWords.isVisible = isVisible
    }

    override fun observeData() {
        getViewModel().getAllWords().observe(viewLifecycleOwner) { data ->
            getViewModel().checkDatabaseEmpty(data)
            setListData(data)
            showContent(true)
        }
        getViewModel().checkDatabaseEmptyLiveData().observe(viewLifecycleOwner) { boolean ->
            if (boolean) stateDataEmpty(true) else stateDataEmpty(false)
        }
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if (query != null) {
            searchKosaKata(query)
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        if (newText != null) {
            searchKosaKata(newText)
        }
        return true
    }

    override fun stateDataEmpty(isVisible: Boolean) {
        if (isVisible) {
            getViewBinding().tvEmptyData.visibility = View.VISIBLE
            getViewBinding().ivEmptyData.visibility = View.VISIBLE
        } else {
            getViewBinding().tvEmptyData.visibility = View.GONE
            getViewBinding().ivEmptyData.visibility = View.GONE
        }
    }
}