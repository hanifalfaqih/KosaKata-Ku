package id.allana.kosakata.ui.kosakata

import androidx.lifecycle.LiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.local.entity.Word

interface KosaKataContract {
    interface View : BaseContract.BaseView {
        fun setupRecyclerView()
        fun setListData(data: List<Word>)
        fun searchKosaKata(query: String)
        fun setupMenu()
        fun stateDataEmpty(isVisible: Boolean)
    }

    interface ViewModel : BaseContract.BaseViewModel {
        fun getAllWords(): LiveData<List<Word>>
        fun getAllWordsByNewest(): LiveData<List<Word>>
        fun getAllWordsAZ(): LiveData<List<Word>>
        fun getAllWordsZA(): LiveData<List<Word>>
        fun checkDatabaseEmpty(data: List<Word>)
        fun checkDatabaseEmptyLiveData(): LiveData<Boolean>
        fun searchKosaKata(searchQuery: String): LiveData<List<Word>>
    }

    interface Repository : BaseContract.BaseRepository {
        fun getAllWords(): LiveData<List<Word>>
        fun getAllWordsByNewest(): LiveData<List<Word>>
        fun getAllWordsAZ(): LiveData<List<Word>>
        fun getAllWordsZA(): LiveData<List<Word>>
        fun searchKosaKata(searchQuery: String): LiveData<List<Word>>
    }
}