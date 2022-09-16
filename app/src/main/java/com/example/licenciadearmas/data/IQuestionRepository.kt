package com.example.licenciadearmas.data

interface IQuestionRepository {
  suspend  fun getQuestionList(section: Sections):  Result<List<Question>>
}
