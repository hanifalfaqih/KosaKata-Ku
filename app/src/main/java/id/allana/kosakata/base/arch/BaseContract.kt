package id.allana.kosakata.base.arch

interface BaseContract {
    interface BaseView {
        fun observeData()
        fun showContent(isVisible: Boolean)
        fun showError(isErrorEnabled: Boolean, msg: String? = null)
    }

    interface BaseViewModel {
        fun logResponse(msg : String?)
    }

    interface BaseRepository {
        fun logResponse(msg : String?)
    }
}