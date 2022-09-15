package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.platform.ComposeView
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {
    private val viewModel: TestViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getQuestions()

        return ComposeView(requireContext()).apply {
            setContent {
                val question = viewModel.question.observeAsState()
                question.value?.let { text->
                    question.value?.let { answers ->

                        TestContent(
                            text.text, answers.answersList
                        )
                    }
                }
            }
        }
    }
}


@Composable
fun TestContent(
    questionText: String,
    answers: List<String>
) {
    Column() {
        QuestionCard(questionText = questionText)
        AnswersCard(answers = answers)


    }
}

@Composable
fun QuestionCard(questionText: String) {
    Surface() {
        Text(text = questionText)
    }
}

@Composable
fun AnswersCard(answers: List<String>) {
    Surface() {
        Column() {
            answers.forEach {
                Text(text = it)
            }

        }
    }
}