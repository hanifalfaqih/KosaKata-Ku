package id.allana.kosakata.ui.kosakata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.local.entity.Word

class KosaKataViewModel(private val repository: KosaKataRepository) : BaseViewModelImpl(), KosaKataContract.ViewModel {

    private val checkDatabaseEmptyLiveData = MutableLiveData<Boolean>()

    override fun getAllWords(): LiveData<List<Word>> {
        return repository.getAllWords()
    }

    override fun getAllWordsByNewest(): LiveData<List<Word>> {
        return repository.getAllWordsByNewest()
    }

    override fun getAllWordsAZ(): LiveData<List<Word>> {
        return repository.getAllWordsAZ()
    }

    override fun getAllWordsZA(): LiveData<List<Word>> {
        return repository.getAllWordsZA()
    }

    override fun checkDatabaseEmpty(data: List<Word>) {
        checkDatabaseEmptyLiveData.value = data.isEmpty()
    }

    override fun checkDatabaseEmptyLiveData(): LiveData<Boolean> = checkDatabaseEmptyLiveData


    override fun searchKosaKata(searchQuery: String): LiveData<List<Word>> {
        return repository.searchKosaKata(searchQuery)
    }
}