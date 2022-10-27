package com.mbp.licenciadearmas.data

data class QuestionRepository(
    val localDataSource: ILocalDataSource
) : IQuestionRepository {
    override suspend fun getQuestionList(section: Section): Result<List<Question>> {

        return localDataSource.getQuestionList(section)
    }
}
