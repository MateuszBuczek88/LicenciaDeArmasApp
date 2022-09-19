package com.example.licenciadearmas.data

interface ILocalDataSource {
    suspend  fun getQuestionList(section: Sections):  Result<List<Question>>
}