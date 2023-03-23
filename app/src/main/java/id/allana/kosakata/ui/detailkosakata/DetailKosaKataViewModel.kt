package id.allana.kosakata.ui.detailkosakata

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import id.allana.kosakata.base.arch.BaseViewModelImpl
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.entity.Word
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class DetailKosaKataViewModel(private val repository: DetailKosaKataRepository) :
    BaseViewModelImpl(),
    DetailKosaKataContract.ViewModel {

    private val updateWordResultLiveData = MutableLiveData<Resource<Number>>()
    private val deleteWordResultLiveData = MutableLiveData<Resource<Number>>()

    override fun synthesizeTextToSpeech(text: String, context: Context) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.synthesizeTextToSpeech(text, context)
        }
    }

    override fun updateWordLiveData(): MutableLiveData<Resource<Number>> = updateWordResultLiveData

    override fun updateWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val updateRowId = repository.updateWord(word)
                viewModelScope.launch(Dispatchers.Main) {
                    if (updateRowId > 0) {
                        updateWordResultLiveData.value = Resource.Success(updateRowId)
                    } else {
                        updateWordResultLiveData.value = Resource.Error("", updateRowId)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    updateWordResultLiveData.value = Resource.Error(e.message.toString())
                }
            }
        }
    }

    override fun deleteWord(word: Word) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val deleteRowId = repository.deleteWord(word)
                viewModelScope.launch(Dispatchers.Main) {
                    if (deleteRowId > 0) {
                        deleteWordResultLiveData.value = Resource.Success(deleteRowId)
                    } else {
                        deleteWordResultLiveData.value = Resource.Error("", deleteRowId)
                    }
                }
            } catch (e: Exception) {
                viewModelScope.launch(Dispatchers.Main) {
                    deleteWordResultLiveData.value = Resource.Error(e.message.toString())
                }
            }
        }
    }

    override fun deleteWordLiveData(): MutableLiveData<Resource<Number>> = deleteWordResultLiveData
    override fun deleteCacheFile() {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteCacheFile()
        }
    }
}