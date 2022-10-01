package com.example.licenciadearmas

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.licenciadearmas.data.IQuestionRepository
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Section
import kotlinx.coroutines.*

class TestViewModel(val repository: IQuestionRepository) : ViewModel() {


    private fun getQuestions() {
        viewModelScope.launch {

            Section.values().map { sections ->
                async {
                    repository.getQuestionList(sections).map { it.shuffled().take(sections.toTest) }
                }
            }
                .awaitAll().let { resultList ->
                    runCatching {
                        resultList.flatMap { it.getOrThrow() }
                    }.fold(
                        onSuccess = {
                            testQuestionsList = it.toMutableList()
                            _question.value = testQuestionsList.firstOrNull()
                            _questionsLeft.value = testQuestionsList.size
                            _testScreenState.value = TestScreenState.ShowQuestion
                        },
                        onFailure = {
                            _testScreenState.value = TestScreenState.LoadError
                        })
                }
        }
    }

    private var testQuestionsList: MutableList<Question> = mutableListOf()

    private val _testScreenState = MutableLiveData(TestScreenState.IsLoading)
    val testScreenState: LiveData<TestScreenState>
        get() = _testScreenState
    private val _questionsLeft = MutableLiveData(0)
    val questionsLeft: LiveData<Int>
        get() = _questionsLeft

    private val _question = MutableLiveData<Question?>(null)
    val question: LiveData<Question?>
        get() = _question

    private val _rightAnswers = MutableLiveData(0)
    val rightAnswers: LiveData<Int>
        get() = _rightAnswers

    private val _wrongAnswers = MutableLiveData(0)
    val wrongAnswers: LiveData<Int>
        get() = _wrongAnswers

    fun checkAnswer(userAnswer: String) {
        if (question.value?.rightAnswer == userAnswer) {
            _rightAnswers.value = _rightAnswers.value?.plus(1)
            nextQuestion()

        } else {
            nextQuestion()
            _wrongAnswers.value = _wrongAnswers.value?.plus(1)
        }
    }

    private fun nextQuestion() {
        testQuestionsList.removeFirstOrNull()
        _questionsLeft.value = testQuestionsList.size
        if (testQuestionsList.isNotEmpty()) {

            _question.value = testQuestionsList.first()
        } else {
            _testScreenState.value = TestScreenState.ShowResult
        }
    }

    init {
        getQuestions()
    }
}