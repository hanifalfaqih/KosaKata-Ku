package id.allana.kosakata.ui.tambahkata

import android.widget.Toast
import com.google.android.material.snackbar.Snackbar
import id.allana.kosakata.R
import id.allana.kosakata.base.arch.BaseBottomSheetDialogFragment
import id.allana.kosakata.base.arch.GenericViewModelFactory
import id.allana.kosakata.data.Resource
import id.allana.kosakata.data.local.database.WordDatabase
import id.allana.kosakata.data.local.datasource.WordDataSourceImpl
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.databinding.FragmentTambahKataDialogBinding

class TambahKataDialogFragment : BaseBottomSheetDialogFragment<FragmentTambahKataDialogBinding, TambahKataDialogViewModel>(FragmentTambahKataDialogBinding::inflate), TambahKataDialogContract.View {

    companion object {
        const val TAG = "TambahKataDialogFragment"
    }

    private var word: Word? = null

    override fun initView() {
        setClickListener()
    }

    override fun setClickListener() {
        getViewBinding().btnAddNewWord.setOnClickListener {
            insertWord()
        }
    }

   override fun insertWord() {
        if (validateForm()) {
            word = Word(
                foreignWord = getViewBinding().etForeignWord.text.toString(),
                meaningWord = getViewBinding().etMeaningWord.text.toString()
            )
            word?.let { getViewModel().insertWord(it) }
        }
    }

    override fun validateForm(): Boolean {
        val foreignWord = getViewBinding().etForeignWord.text.toString()
        val meaning = getViewBinding().etMeaningWord.text.toString()
        val isFormValid: Boolean

        when {
            foreignWord.isEmpty() -> {
                isFormValid = false
                getViewBinding().etForeignWord.error = getString(R.string.text_error_et_foreign_empty)
            }
            meaning.isEmpty() -> {
                isFormValid = false
                getViewBinding().etMeaningWord.error = getString(R.string.text_error_et_meaning_empty)
            }
            else -> {
                isFormValid = true
            }
        }
        return isFormValid
    }

    override fun initViewModel(): TambahKataDialogViewModel {
        val dataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireActivity()).wordDao())
        val repository = TambahKataDialogRepository(dataSource)
        return GenericViewModelFactory(TambahKataDialogViewModel(repository)).create(TambahKataDialogViewModel::class.java)
    }

    override fun observeData() {
        super.observeData()
        getViewModel().insertWordLiveData().observe(this) {
            when (it) {
                is Resource.Success -> {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), R.string.text_add_success, Toast.LENGTH_SHORT).show()
                    this.dismiss()
                }
                else -> {
                    Snackbar.make(requireActivity().findViewById(android.R.id.content), R.string.text_add_failed, Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


}