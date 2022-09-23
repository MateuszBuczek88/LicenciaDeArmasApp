package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Section
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class LearnViewModel(val repository: IQuestionRepository, val section: Section) : ViewModel() {
    private var questionList: MutableList<Question> = mutableListOf()
    fun getQuestions() {
        viewModelScope.launch(Dispatchers.Main) {
             repository.getQuestionList(section).fold(onSuccess = {
                    questionList = it.toMutableList()
                    _question.value = questionList.firstOrNull()
                }, onFailure = {
                    _loadError.value = true
                })
                _isLoading.value = false
        }
    }

    private val _loadError = MutableLiveData<Boolean>(false)
    val loadError: LiveData<Boolean>
        get() = _loadError

    private val _isLoading = MutableLiveData<Boolean>(true)
    val isLoading: LiveData<Boolean>
        get() = _isLoading

    private val _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question

    private val _showAnswer = MutableLiveData(false)
    val showAnswer: LiveData<Boolean>
        get() = _showAnswer

    private val _showCongrats = MutableLiveData<Boolean>(false)
    val showCongrats: LiveData<Boolean>
        get() = _showCongrats

    fun nextQuestion() {
        _question.value?.let { questionList.add(it) }
        questionList.removeFirst()
        _question.value = questionList.first()
    }

    fun prevQuestion() {
        _question.value = questionList.last()
        question.value?.let { questionList.add(0, it) }
        questionList.removeLast()
    }

    fun showAnswer() {
        _showAnswer.value = true
    }

    fun rightAnswer() {
        questionList.removeFirstOrNull()
        if (questionList.isNotEmpty()) {
            _question.value = questionList.first()
            _showAnswer.value = false
        } else {
            _showCongrats.value = true
        }
    }

    fun wrongAnswer() {
        if (questionList.isNotEmpty()) {
            _question.value?.let { questionList.add(it) }
            questionList.removeFirst()
            _question.value = questionList.first()
        }
        _showAnswer.value = false
    }

    init {
        getQuestions()
    }
}