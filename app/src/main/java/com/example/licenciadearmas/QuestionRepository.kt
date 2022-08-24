package com.example.licenciadearmas

data class QuestionRepository(val questions: List<Question>) : IQuestionRepository {
    val question = questions.listIterator()
    override fun getQuestionList(): List<Question> {
        return questions
    }


    override fun nextQuestion(): Question {
        return question.next()
    }

    override fun prevQuestion(): Question {
        return question.previous()
    }

}
