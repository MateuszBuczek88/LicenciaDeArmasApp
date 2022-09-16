package com.example.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {
    private val viewModel: TestViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {


        return ComposeView(requireContext()).apply {
            setContent {

                val question by viewModel.question.observeAsState()
                val isAnswerCorrect = viewModel.isAnswerCorrect.observeAsState(initial = false)
                val rightAnswers by viewModel.rightAnswers.observeAsState()
                val wrongAnswers = viewModel.wrongAnswers.observeAsState()
                question?.let { text ->
                    question?.let { answers ->
                        Column {
                            TestContent(
                                text.text,
                                answers.answersList,
                                onAnswerButtonClick = { viewModel.checkAnswer(it) })
                            Text(text = isAnswerCorrect.value.toString())
                            Text(text = question!!.rightAnswer)
                            Text(text = "rightanswers:${rightAnswers}/wronganswers:${wrongAnswers.value}")
                        }
                    }
                }
            }
        }
    }
}


@Composable
fun TestContent(
    questionText: String,
    answers: List<String>,
    onAnswerButtonClick: (String) -> Unit
) {
    Column {
        QuestionCard(questionText = questionText)
        AnswersCard(answers = answers, onAnswerButtonClick = onAnswerButtonClick)
    }
}

@Composable
fun QuestionCard(questionText: String) {
    Surface {
        Text(text = questionText)
    }
}

@Composable
fun AnswersCard(answers: List<String>, onAnswerButtonClick: (String) -> Unit) {
    Column {

        answers.forEach {

            Surface(
                shape = MaterialTheme.shapes.small,
                elevation = 1.dp,
                color = Color.White,
                modifier = Modifier
            ) {
                Text(text = it)
            }
            Button(onClick = { onAnswerButtonClick(it) }) {
                Text(text = it)
            }
        }
    }
}