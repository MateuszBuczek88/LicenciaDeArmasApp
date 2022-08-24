package com.example.licenciadearmas.data

import com.example.licenciadearmas.data.questions.*

data class QuestionRepository(
    val questionsTema1: List<Question>,
    val questionsTema2: List<Question>,
    val questionsTema3: List<Question>,
    val questionsTema4: List<Question>,
    val questionsTema5: List<Question>,
    val questionsTema6: List<Question>,
    val questionsTema7: List<Question>
) : IQuestionRepository {

    override fun getQuestionList(section: Sections): List<Question> =
        when (section) {
            Sections.Tema1 -> questionsTema1
            Sections.Tema2 -> questionsTema2
            Sections.Tema3 -> questionsTema3
            Sections.Tema4 -> questionsTema4
            Sections.Tema5 -> questionsTema5
            Sections.Tema6 -> questionsTema6
            Sections.Tema7 -> questionsTema7

        }
}
