package com.example.licenciadearmas.data

interface IQuestionRepository {
  suspend  fun getQuestionListFromLocalData(section: Sections):  Result<List<Question>>
}
