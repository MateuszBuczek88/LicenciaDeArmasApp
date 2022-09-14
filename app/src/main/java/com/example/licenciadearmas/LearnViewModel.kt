package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Sections

class LearnViewModel(val repository: IQuestionRepository, val section: Sections) : ViewModel() {
    private val questionList: MutableList<Question> =
        repository.getQuestionList(section).toMutableList()
    private val _question = MutableLiveData(questionList.first())
    val question: LiveData<Question>
        get() = _question

    private var _showAnswer = MutableLiveData(false)
    val showAnswer: LiveData<Boolean>
        get() = _showAnswer

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
        } else {/*TODO*/}
        _showAnswer.value = false
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