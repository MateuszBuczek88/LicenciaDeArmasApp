package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Sections
import kotlinx.coroutines.launch

class LearnViewModel(val repository: IQuestionRepository, val section: Sections) : ViewModel() {
   lateinit var questionList: MutableList<Question>
    fun getQuestions() {
         viewModelScope.launch {
             questionList = repository.getQuestionList(section).toMutableList()
             _question.value = questionList.first()

        }
    }

    private var _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question

    private var _showAnswer = MutableLiveData(false)
    val showAnswer: LiveData<Boolean>
        get() = _showAnswer

    private var _showCongrats = MutableLiveData<Boolean>(false)
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
}