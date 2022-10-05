package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LearnViewModel(val repository: IQuestionRepository, val section: Section) : ViewModel() {
    private var questionList: MutableList<Question> = mutableListOf()
    private fun getQuestions() {
        viewModelScope.launch(Dispatchers.Main) {
            repository.getQuestionList(section).fold(
                onSuccess = {
                    questionList = it.toMutableList()
                    _question.value = questionList.firstOrNull()
                    _questionsLeft.value = questionList.size
                    _learnScreenState.value = LearnScreenState.ShowQuestion
                },
                onFailure = {
                    _learnScreenState.value = LearnScreenState.LoadError
                })
        }
    }

    private val _learnScreenState = MutableLiveData(LearnScreenState.IsLoading)
    val learnScreenState: LiveData<LearnScreenState>
        get() = _learnScreenState

    private val _questionsLeft = MutableLiveData(0)
    val questionsLeft: LiveData<Int>
        get() = _questionsLeft

    private val _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question

    private var _chosenAnswer = MutableLiveData("")
    val chosenAnswer :LiveData<String>
    get() = _chosenAnswer

    fun showAnswer(chosenAnswer: String) {
        _learnScreenState.value = LearnScreenState.ShowAnswer
        _chosenAnswer.value = chosenAnswer
    }

    fun loadNextQuestion() {
        if (_chosenAnswer.value == _question.value?.rightAnswer) rightAnswer() else wrongAnswer()
        _questionsLeft.value = questionList.size
    }

    private fun rightAnswer() {
        questionList.removeFirstOrNull()
        if (questionList.isNotEmpty()) {
            _question.value = questionList.first()
            _learnScreenState.value = LearnScreenState.ShowQuestion
        } else {
            _learnScreenState.value = LearnScreenState.ShowResult
        }
    }

    private fun wrongAnswer() {
        if (questionList.isNotEmpty()) {
            _question.value?.let { questionList.add(it) }
            questionList.removeFirst()
            _question.value = questionList.first()
            _learnScreenState.value = LearnScreenState.ShowQuestion
        }
    }

    init {
        getQuestions()
    }
}