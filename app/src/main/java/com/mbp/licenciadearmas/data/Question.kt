package com.mbp.licenciadearmas.data

data class Question(
    val text: String,
    val answersList: List<String>,
    val rightAnswer: String
)
