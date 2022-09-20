package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Sections
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.awaitAll

class TestViewModel(val repository: IQuestionRepository) : ViewModel() {
    init {
        getQuestions()
    }

    fun getQuestions() {
        viewModelScope.launch {

            Sections.values().map { sections ->
                async {
                    repository.getQuestionListFromLocalData(sections).map { it.shuffled().take(sections.toTest) }
                }
            }
                .awaitAll().let { resultList ->
                    runCatching {
                        resultList.flatMap { it.getOrThrow() }
                    }.fold({
                        testQuestionsList = it.toMutableList()
                        _question.value = testQuestionsList.first()
                    },
                        {_loadError.value = true})
                }
        }
    }

    lateinit var testQuestionsList: MutableList<Question>
    private val _loadError = MutableLiveData<Boolean>(false)
    val loadError: LiveData<Boolean>
        get() = _loadError
    private val _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question
    private val _isAnswerCorrect = MutableLiveData<Boolean>(false)
    val isAnswerCorrect: LiveData<Boolean>
        get() = _isAnswerCorrect
    private val _rightAnswers = MutableLiveData<Int>(0)
    val rightAnswers: LiveData<Int>
        get() = _rightAnswers
    private val _wrongAnswers = MutableLiveData<Int>(0)
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