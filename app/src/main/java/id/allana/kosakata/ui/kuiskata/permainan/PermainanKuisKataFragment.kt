package id.allana.kosakata.ui.kuiskata.permainan

import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import id.allana.kosakata.R
import id.allana.kosakata.base.arch.BaseFragment
import id.allana.kosakata.base.arch.GenericViewModelFactory
import id.allana.kosakata.data.local.database.WordDatabase
import id.allana.kosakata.data.local.datasource.WordDataSourceImpl
import id.allana.kosakata.data.local.entity.Word
import id.allana.kosakata.databinding.FragmentPermainanKuisKataBinding

class PermainanKuisKataFragment: BaseFragment<FragmentPermainanKuisKataBinding, PermainanKuisKataViewModel>(FragmentPermainanKuisKataBinding::inflate),
PermainanKuisKataContract.View {

    private var currentWordIndex = 0
    private lateinit var words: List<Word>

    override fun initView() {
        getViewBinding().etArti.addTextChangedListener(textWatcher)
        setButton(false)
        getViewBinding().btnCheckAnswer.setOnClickListener {
            val input = getViewBinding().etArti.text.toString()
            getViewModel().correctAnswerLiveData().value = input == words[currentWordIndex].meaningWord
        }
    }

    private val textWatcher = object: TextWatcher {
        override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

        override fun onTextChanged(text: CharSequence?, p1: Int, p2: Int, p3: Int) {
            val answer = getViewBinding().etArti.text.toString()
            if (answer.isNotEmpty() && answer.isNotBlank()) setButton(true) else setButton(false)
        }

        override fun afterTextChanged(p0: Editable?) {}

    }

    override fun initViewModel(): PermainanKuisKataViewModel {
        val dataSource = WordDataSourceImpl(WordDatabase.getDatabase(requireActivity()).wordDao())
        val repository = PermainanKuisKataRepository(dataSource)
        return GenericViewModelFactory(PermainanKuisKataViewModel(repository)).create(PermainanKuisKataViewModel::class.java)
    }

    override fun getShuffledList(list: List<Word>): List<Word> {
        return list.shuffled().take(5)
    }

    override fun observeData() {
        getViewModel().getAllWords().observe(viewLifecycleOwner) { list ->
            words = getShuffledList(list)
            setText(words)

            getViewModel().isGameOverLiveData().observe(viewLifecycleOwner) { boolean ->
                if (boolean) {
                    words = getShuffledList(list)
                    showKuisKataDialog()
                } else {
                    getViewBinding().btnCheckAnswer.setOnClickListener {
                        val input = getViewBinding().etArti.text.toString()
                        getViewModel().correctAnswerLiveData().value = input == words[currentWordIndex].meaningWord
                    }
                }
            }
        }
        getViewModel().correctAnswerLiveData().observe(viewLifecycleOwner) { isCorrect ->
            if (isCorrect) {
                getViewBinding().btnCheckAnswer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
                getViewBinding().btnCheckAnswer.text = getString(R.string.text_next_question)
                getViewBinding().btnCheckAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                getViewBinding().tvResponseAnswer.isVisible = true
                getViewBinding().tvMeaningDetail.isVisible = true
                getViewBinding().tvResponseAnswer.apply {
                    text = getString(R.string.text_correct_answer)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorCorrect))
                }
                getViewBinding().tvMeaningDetail.text = words[currentWordIndex].meaningWord
                getViewBinding().etArti.isEnabled = false

                getViewBinding().btnCheckAnswer.setOnClickListener {
                    nextWord()
                }
            } else {
                getViewBinding().btnCheckAnswer.setBackgroundColor(ContextCompat.getColor(requireContext(), R.color.primaryColor))
                getViewBinding().btnCheckAnswer.text = getString(R.string.text_next_question)
                getViewBinding().btnCheckAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))

                getViewBinding().tvResponseAnswer.isVisible = true
                getViewBinding().tvMeaningDetail.isVisible = true
                getViewBinding().tvResponseAnswer.apply {
                    text = getString(R.string.text_wrong_answer)
                    setTextColor(ContextCompat.getColor(requireContext(), R.color.colorWrong))
                }
                getViewBinding().tvMeaningDetail.text = words[currentWordIndex].meaningWord
                getViewBinding().etArti.isEnabled = false

                getViewBinding().btnCheckAnswer.setOnClickListener {
                    nextWord()
                }
            }
        }
        getViewModel().currentIndexWordLiveData().observe(viewLifecycleOwner) {
            getViewBinding().etArti.addTextChangedListener(textWatcher)
            setButton(false)
            setButtonDefault()
            setText(words)

            getViewBinding().btnCheckAnswer.setOnClickListener {
                val input = getViewBinding().etArti.text.toString()
                getViewModel().correctAnswerLiveData().value = input == words[currentWordIndex].meaningWord
            }
        }
    }

    override fun nextWord() {
        if (currentWordIndex < words.size - 1) {
            currentWordIndex++
            getViewModel().currentIndexWordLiveData().value = currentWordIndex
        } else {
            getViewModel().isGameOverLiveData().value = true
        }
    }

    override fun showKuisKataDialog() {
        AlertDialog.Builder(requireContext()).apply {
            setTitle(R.string.title_alert_dialog_kuis_kata)
            setMessage(getString(R.string.message_alert_dialog_kuis_kata))
                .setPositiveButton(getString(R.string.text_positive_button)) { dialog, _ ->
                    getViewBinding().etArti.addTextChangedListener(textWatcher)
                    setButton(false)
                    setButtonDefault()
                    currentWordIndex = 0
                    setText(words)
                    dialog.dismiss()
                    getViewModel().isGameOverLiveData().value = false
                }
                .setNegativeButton(getString(R.string.text_negative_button)) { _, _ ->
                    findNavController().popBackStack()
                }
        }.show()
    }

    override fun setButtonDefault() {
        getViewBinding().etArti.text?.clear().toString()
        getViewBinding().etArti.clearFocus()
        getViewBinding().etArti.isEnabled = true
        getViewBinding().btnCheckAnswer.text = getString(R.string.text_check_answer)

        getViewBinding().tvResponseAnswer.isVisible = false
        getViewBinding().tvMeaningDetail.isVisible = false

        getViewBinding().btnCheckAnswer.setTextColor(ContextCompat.getColor(requireContext(), R.color.white))
    }

    override fun setText(words: List<Word>) {
        val word = words[currentWordIndex].foreignWord
        getViewBinding().tvWordDetail.text = word
    }

    override fun setButton(status: Boolean) {
        if (status) {
            getViewBinding().btnCheckAnswer.isEnabled = true
            getViewBinding().btnCheckAnswer.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.primaryColor)
        } else {
            getViewBinding().btnCheckAnswer.isEnabled = false
            getViewBinding().btnCheckAnswer.backgroundTintList = ContextCompat.getColorStateList(requireContext(), R.color.button_disable)
        }
    }
}
