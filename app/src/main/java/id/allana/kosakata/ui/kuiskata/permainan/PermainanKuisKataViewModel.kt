package id.allana.kosakata.ui.kuiskata.permainan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.local.entity.Word

class PermainanKuisKataViewModel(private val repository: PermainanKuisKataRepository) : BaseViewModelImpl(),
    PermainanKuisKataContract.ViewModel {

    private val correctAnswerResponseLiveData = MutableLiveData<Boolean>()
    private val currentIndexWordResponseLiveData = MutableLiveData<Int>()
    private val isGameOverLiveData = MutableLiveData<Boolean>()

    override fun getAllWords(): LiveData<List<Word>> {
        return repository.getAllWords()
    }

    override fun correctAnswerLiveData(): MutableLiveData<Boolean> = correctAnswerResponseLiveData

    override fun currentIndexWordLiveData(): MutableLiveData<Int> = currentIndexWordResponseLiveData

    override fun isGameOverLiveData(): MutableLiveData<Boolean> = isGameOverLiveData

}