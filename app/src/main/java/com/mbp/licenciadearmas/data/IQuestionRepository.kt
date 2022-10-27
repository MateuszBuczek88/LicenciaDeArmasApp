package com.mbp.licenciadearmas.data

interface IQuestionRepository {
  suspend  fun getQuestionList(section: Section):  Result<List<Question>>
}
