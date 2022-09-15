package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Sections
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.awaitAll

class TestViewModel(val repository: IQuestionRepository) : ViewModel() {
    lateinit var testQuestionsList: MutableList<Question>
    fun getQuestions() {
        viewModelScope.launch {

            testQuestionsList = Sections.values().map {
                async { repository.getQuestionList(it).shuffled().take(it.toTest) }
            }
                .awaitAll().flatten().toMutableList()

            _question.value = testQuestionsList.first()
        }
    }

    private var _question = MutableLiveData<Question?>()
    val question: LiveData<Question?>
        get() = _question
}