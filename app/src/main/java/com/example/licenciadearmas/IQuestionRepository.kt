package com.example.licenciadearmas

interface IQuestionRepository {
    fun getQuestionList(): List<Question>
    fun nextQuestion(): Question
    fun prevQuestion(): Question
}
