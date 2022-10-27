package com.mbp.licenciadearmas.data

interface ILocalDataSource {
    suspend  fun getQuestionList(section: Section):  Result<List<Question>>
}