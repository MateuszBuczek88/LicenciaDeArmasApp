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
    private val _question = MutableLiveData<Question>(questionList.first())
    val question: LiveData<Question>
        get() = _question


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
}