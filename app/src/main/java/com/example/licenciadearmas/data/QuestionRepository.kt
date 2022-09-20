package com.example.licenciadearmas.data

import kotlinx.coroutines.delay

data class QuestionRepository(val localDataSource: ILocalDataSource
) : IQuestionRepository {

    override suspend fun getQuestionList(section: Section): Result<List<Question>> {
        delay(1000)
        return localDataSource.getQuestionList(section)
    }
}
