package id.allana.kosakata.ui.detailkosakata

import android.content.Context
import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.entity.Word

interface DetailKosaKataContract {
    interface View: BaseContract.BaseView {
        fun getIntentData()
        fun setupDataToView()
        fun setBookmark(isBookmark: Boolean)
        fun isOnline(context: Context): Boolean
        fun showDeleteDialog()
        fun checkInternetAvailable(context: Context): Boolean
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun synthesizeTextToSpeech(text: String, context: Context)
        fun updateWordLiveData(): MutableLiveData<Resource<Number>>
        fun updateWord(word: Word)
        fun deleteWord(word: Word)
        fun deleteWordLiveData(): MutableLiveData<Resource<Number>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun synthesizeTextToSpeech(text: String, context: Context)
        suspend fun updateWord(word: Word): Int
        suspend fun deleteWord(word: Word): Int
    }
}