package com.example.licenciadearmas.data

class InMemoryDataSource(
    val questionsTema1: List<Question>,
    val questionsTema2: List<Question>,
    val questionsTema3: List<Question>,
    val questionsTema4: List<Question>,
    val questionsTema5: List<Question>,
    val questionsTema6: List<Question>,
    val questionsTema7: List<Question>
) : ILocalDataSource {

    override suspend fun getQuestionList(section: Section): Result<List<Question>> {
        return when (section) {
            Section.Tema1 -> runCatching { questionsTema1 }
            Section.Tema2 -> runCatching { questionsTema2 }
            Section.Tema3 -> runCatching { questionsTema3 }
            Section.Tema4 -> runCatching { questionsTema4 }
            Section.Tema5 -> runCatching { questionsTema5 }
            Section.Tema6 -> runCatching { questionsTema6 }
            Section.Tema7 -> runCatching { questionsTema7 }
        }
    }
}