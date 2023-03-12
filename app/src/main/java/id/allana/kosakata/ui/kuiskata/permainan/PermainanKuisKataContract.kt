package id.allana.kosakata.ui.kuiskata.permainan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.local.entity.Word

interface PermainanKuisKataContract {

    interface View: BaseContract.BaseView {
        fun nextWord()
        fun getShuffledList(list: List<Word>): List<Word>
        fun showKuisKataDialog()
        fun setButtonDefault()
        fun setText(words: List<Word>)
        fun setButton(status: Boolean)
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllWords(): LiveData<List<Word>>
        fun correctAnswerLiveData(): MutableLiveData<Boolean>
        fun currentIndexWordLiveData(): MutableLiveData<Int>
        fun isGameOverLiveData(): MutableLiveData<Boolean>
    }

    interface Repository: BaseContract.BaseRepository {
        fun getAllWords(): LiveData<List<Word>>
    }

}