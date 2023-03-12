package id.allana.kosakata.ui.tambahkata

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TambahKataDialogViewModel(private val repository: TambahKataDialogRepository): BaseViewModelImpl(), TambahKataDialogContract.ViewModel {

    private val insertWordResultLiveData = MutableLiveData<Resource<Number>>()

    override fun insertWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val insertedRowId = repository.insertWord(word)
                viewModelScope.launch(Dispatchers.Main) {
                    if (insertedRowId > 0) {
                        insertWordResultLiveData.value = Resource.Success(insertedRowId)
                    } else {
                        insertWordResultLiveData.value = Resource.Error("", insertedRowId)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    insertWordResultLiveData.value = Resource.Error(e.message.toString())
                }
            }
        }
    }

    override fun insertWordLiveData(): MutableLiveData<Resource<Number>> = insertWordResultLiveData
}