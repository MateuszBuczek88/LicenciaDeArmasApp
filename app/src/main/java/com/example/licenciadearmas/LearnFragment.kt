package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.dp
import androidx.navigation.fragment.navArgs
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Sections
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LearnFragment : Fragment() {
    private val viewModel: LearnViewModel by viewModel { parametersOf(section) }
    private val navArgs: LearnFragmentArgs by navArgs()
    private val section: Sections by lazy { navArgs.section }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getQuestions()
        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {

                    val question: Question? by viewModel.question.observeAsState()
                    val showAnswer by viewModel.showAnswer.observeAsState()
                    val showCongrats by viewModel.showCongrats.observeAsState()

                    if (showCongrats != true) {
                        question?.let {
                            showAnswer?.let { showAnswer ->

                                LearnContent(
                                    question = it,
                                    onNextClick = { viewModel.nextQuestion() },
                                    onPrevClick = { viewModel.prevQuestion() },
                                    showButtons = showAnswer,
                                    onQuestionClick = { viewModel.showAnswer() },
                                    rightAnswer = { viewModel.rightAnswer() },
                                    wrongAnswer = { viewModel.wrongAnswer() }
                                )
                            }
                        }
                    } else {
                        AllQuestionsLearnedCard()
                    }
                }
            }
        }
    }
}

@Composable
fun LearnContent(
    question: Question,
    onNextClick: () -> Unit,
    onPrevClick: () -> Unit,
    onQuestionClick: () -> Unit,
    showButtons: Boolean,
    rightAnswer: () -> Unit,
    wrongAnswer: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        QuestionCard(questionText = question.text, onQuestionClick = onQuestionClick)
        Spacer(modifier = Modifier.height(20.dp))

        if (showButtons) {
            AnswerCard(answerText = question.rightAnswer)
            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Button(onClick = rightAnswer) {
                    Text(text = "OK")
                }
                Spacer(modifier = Modifier.width(20.dp))

                Button(onClick = wrongAnswer) {
                    Text(text = "WRONG")
                }
            }
        }
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }
        Button(onClick = onPrevClick) {
            Text(text = "prev")
        }
    }
}

@Composable
fun QuestionCard(questionText: String, onQuestionClick: () -> Unit) {
    Surface(modifier = Modifier.clickable(onClick = onQuestionClick)) {
        Text(text = questionText)
    }
}

@Composable
fun AnswerCard(answerText: String) {
    Surface {
        Text(text = answerText)
    }
}

@Composable
fun AllQuestionsLearnedCard() {
    Surface {
        Column(verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally)
        {
            Text(text = "Congrats", style = MaterialTheme.typography.body1)
            Button(onClick = { /*TODO*/ }) {
                Text(text = "Again")
            }

            Button(onClick = { /*TODO*/ }) {
                Text(text = "Home")
            }
        }
    }
}
