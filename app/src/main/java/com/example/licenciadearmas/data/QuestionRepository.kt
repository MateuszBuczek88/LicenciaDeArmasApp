package com.example.licenciadearmas.data

import com.example.licenciadearmas.data.questions.*
import kotlinx.coroutines.delay

data class QuestionRepository(
    val questionsTema1: List<Question>,
    val questionsTema2: List<Question>,
    val questionsTema3: List<Question>,
    val questionsTema4: List<Question>,
    val questionsTema5: List<Question>,
    val questionsTema6: List<Question>,
    val questionsTema7: List<Question>
) : IQuestionRepository {

  override suspend fun getQuestionList(section: Sections): List<Question> {
      delay(2000)
      when (section) {
          Sections.Tema1 ->return questionsTema1
          Sections.Tema2 ->return questionsTema2
          Sections.Tema3 ->return questionsTema3
          Sections.Tema4 ->return questionsTema4
          Sections.Tema5 ->return questionsTema5
          Sections.Tema6 ->return questionsTema6
          Sections.Tema7 ->return questionsTema7
      }
  }
}
