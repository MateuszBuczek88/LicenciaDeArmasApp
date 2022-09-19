package com.example.licenciadearmas.data

import com.example.licenciadearmas.data.questions.*
import kotlinx.coroutines.delay

data class QuestionRepository(
    val questionsTema1: Result<List<Question>>,
    val questionsTema2: List<Question>,
    val questionsTema3: List<Question>,
    val questionsTema4: List<Question>,
    val questionsTema5: List<Question>,
    val questionsTema6: List<Question>,
    val questionsTema7: List<Question>
) : IQuestionRepository {

  override suspend fun getQuestionList(section: Sections): Result<List<Question>>  {
      delay(500)
      return when (section) {
          Sections.Tema1 ->  questionsTema1
          Sections.Tema2 -> runCatching { questionsTema2 }
          Sections.Tema3 -> runCatching { questionsTema3 }
          Sections.Tema4 -> runCatching { questionsTema4 }
          Sections.Tema5 -> runCatching { questionsTema5 }
          Sections.Tema6 -> runCatching { questionsTema6 }
          Sections.Tema7 -> runCatching { questionsTema7 }
      }
  }
}
