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
    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {

            testQuestionsList = Sections.values().map { sections ->
                async {
                    repository.getQuestionList(sections).map { it.shuffled().take(sections.toTest) }
                }
            }
                    //implement runCatching in let
                .awaitAll().let { resultList -> resultList.flatMap { it.getOrThrow() }.toMutableList() }

            _question.value = testQuestionsList.first()
        }
    }

    lateinit var testQuestionsList: MutableList<Question>
    private var _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question
    private var _isAnswerCorrect = MutableLiveData<Boolean>(false)
    val isAnswerCorrect: LiveData<Boolean>
        get() = _isAnswerCorrect
    private var _rightAnswers = MutableLiveData<Int>(0)
    val rightAnswers: LiveData<Int>
        get() = _rightAnswers
    private var _wrongAnswers = MutableLiveData<Int>(0)
    val wrongAnswers: LiveData<Int>
        get() = _wrongAnswers


    fun checkAnswer(userAnswer: String) {
        if (question.value?.rightAnswer == userAnswer) {
            _isAnswerCorrect.value = true
            _rightAnswers.value = _rightAnswers.value?.plus(1)
            nextQuestion()


        } else {
            nextQuestion()
            _wrongAnswers.value = _wrongAnswers.value?.plus(1)
        }

    }

    fun nextQuestion() {
        testQuestionsList.removeFirstOrNull()
        _question.value = testQuestionsList.first()
         _isAnswerCorrect.value = false
    }
}