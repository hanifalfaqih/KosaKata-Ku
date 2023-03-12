package id.allana.kosakata.ui.tambahkata

import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.entity.Word

interface TambahKataDialogContract {
    interface View: BaseContract.BaseView {
        fun validateForm(): Boolean
        fun insertWord()
        fun setClickListener()
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun insertWord(word: Word)
        fun insertWordLiveData(): MutableLiveData<Resource<Number>>
    }

    interface Repository: BaseContract.BaseRepository {
        suspend fun insertWord(word: Word): Long
    }
}