package id.allana.kosakata.ui.favoritkata

import androidx.lifecycle.LiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.local.entity.Word

interface FavoritKataContract {

    interface View: BaseContract.BaseView {
        fun setupRecyclerView()
        fun setListData(data: List<Word>)
        fun getData()
        fun stateDataEmpty(isVisible: Boolean)
        fun setupMenu()
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllFavoriteWords(): LiveData<List<Word>>
        fun getAllFavoriteWordsAZ(): LiveData<List<Word>>
        fun getAllFavoriteWordsZA(): LiveData<List<Word>>
        fun checkDatabaseEmpty(data: List<Word>)
        fun checkDatabaseEmptyLiveData(): LiveData<Boolean>
    }

    interface Repository: BaseContract.BaseRepository {
        fun getAllFavoriteWords(): LiveData<List<Word>>
        fun getAllFavoriteWordsAZ(): LiveData<List<Word>>
        fun getAllFavoriteWordsZA(): LiveData<List<Word>>
    }
}