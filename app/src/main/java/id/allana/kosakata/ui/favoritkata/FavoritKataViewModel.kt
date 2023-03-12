package id.allana.kosakata.ui.favoritkata

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.local.entity.Word

class FavoritKataViewModel(private val repository: FavoritKataRepository) : BaseViewModelImpl(), FavoritKataContract.ViewModel {

    private val checkDatabaseEmptyLiveData = MutableLiveData<Boolean>()

    override fun getAllFavoriteWords(): LiveData<List<Word>> {
        return repository.getAllFavoriteWords()
    }

    override fun getAllFavoriteWordsAZ(): LiveData<List<Word>> {
        return repository.getAllFavoriteWordsAZ()
    }

    override fun getAllFavoriteWordsZA(): LiveData<List<Word>> {
        return repository.getAllFavoriteWordsZA()
    }

    override fun checkDatabaseEmpty(data: List<Word>) {
        checkDatabaseEmptyLiveData.value = data.isEmpty()
    }

    override fun checkDatabaseEmptyLiveData(): LiveData<Boolean> = checkDatabaseEmptyLiveData


}