package com.example.licenciadearmas.data

interface IQuestionRepository {
    fun getQuestionList(section: Sections): List<Question>
}
