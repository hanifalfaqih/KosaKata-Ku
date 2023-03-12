package id.allana.kosakata.ui.kuiskata

import androidx.lifecycle.LiveData
import id.allana.kosakata.base.arch.BaseContract
import id.allana.kosakata.data.local.entity.Word

interface KuisKataContract {

    interface View: BaseContract.BaseView {
        fun intentToPermainanKuisKataFragment()
        fun getData()
    }

    interface ViewModel: BaseContract.BaseViewModel {
        fun getAllWords(): LiveData<List<Word>>
    }

    interface Repository: BaseContract.BaseRepository {
        fun getAllWords(): LiveData<List<Word>>
    }
}