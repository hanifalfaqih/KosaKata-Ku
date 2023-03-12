package id.allana.kosakata.ui.favoritkata

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
import id.allana.kosakata.databinding.FragmentFavoritKataBinding
import id.allana.kosakata.ui.adapter.FavoritKosaKataAdapter

class FavoritKataFragment :
    BaseFragment<FragmentFavoritKataBinding, FavoritKataViewModel>(FragmentFavoritKataBinding::inflate),
    FavoritKataContract.View {

    private lateinit var adapter: FavoritKosaKataAdapter

    override fun initView() {
        setupRecyclerView()
        setupMenu()
    }

    override fun initViewModel(): FavoritKataViewModel {
        val dataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireActivity()).wordDao())
        val repository = FavoritKataRepository(dataSource)
        return GenericViewModelFactory(FavoritKataViewModel(repository)).create(FavoritKataViewModel::class.java)
    }

    override fun setupRecyclerView() {
        adapter = FavoritKosaKataAdapter()
        getViewBinding().rvListFavoriteWords.apply {
            layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
            adapter = this@FavoritKataFragment.adapter
        }
    }

    override fun setListData(data: List<Word>) {
        adapter.submitList(data)
    }

    override fun getData() {
        getViewModel().getAllFavoriteWords()
    }

    override fun showContent(isVisible: Boolean) {
        super.showContent(isVisible)
        getViewBinding().rvListFavoriteWords.isVisible = isVisible
    }

    override fun observeData() {
        getViewModel().getAllFavoriteWords().observe(viewLifecycleOwner) { data ->
            getViewModel().checkDatabaseEmpty(data)
            setListData(data)
            showContent(true)
        }
        getViewModel().checkDatabaseEmptyLiveData().observe(viewLifecycleOwner) { boolean ->
            if (boolean) stateDataEmpty(true) else stateDataEmpty(false)
        }
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

    override fun setupMenu() {
        (requireActivity() as MenuHost).addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menu.clear()
                menuInflater.inflate(R.menu.favorit_kata_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                when (menuItem.itemId) {
                    R.id.menu_a_z -> {
                        getViewModel().getAllFavoriteWordsAZ().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                    R.id.menu_z_a -> {
                        getViewModel().getAllFavoriteWordsZA().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                    R.id.menu_newest -> {
                        getViewModel().getAllFavoriteWords().observe(viewLifecycleOwner) { list ->
                            setListData(list)
                        }
                    }
                }
                return true
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)
    }
}