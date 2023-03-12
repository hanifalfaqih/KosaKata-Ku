package id.allana.kosakata.ui.kuiskata

import androidx.lifecycle.LiveData
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.local.entity.Word

class KuisKataViewModel(private val repository: KuisKataRepository) : BaseViewModelImpl(),
    KuisKataContract.ViewModel {

    override fun getAllWords(): LiveData<List<Word>> {
        return repository.getAllWords()
    }



}