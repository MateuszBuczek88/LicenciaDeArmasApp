package com.example.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {
    private val viewModel: TestViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {

                    val question by viewModel.question.observeAsState()
                    val isAnswerCorrect =
                        viewModel.isAnswerCorrect.observeAsState(initial = false)
                    val rightAnswers by viewModel.rightAnswers.observeAsState()
                    val wrongAnswers by viewModel.wrongAnswers.observeAsState()
                    val loadError by viewModel.loadError.observeAsState()
                    val isLoading by viewModel.isLoading.observeAsState()
                    val showResult by viewModel.showResult.observeAsState()
                    if (showResult != true) {
                        if (isLoading != true) {
                            Column {
                                loadError?.let {
                                    TestContent(loadError = it, question = question,
                                        onAnswerButtonClick = { answer ->
                                            viewModel.checkAnswer(
                                                answer
                                            )
                                        })
                                }

                                Text(text = isAnswerCorrect.toString(), color = Color.White)
                                question?.let { Text(text = it.rightAnswer, color = Color.White) }
                                Text(
                                    text = "rightanswers:${rightAnswers}/wronganswers:${wrongAnswers}",
                                    color = Color.White
                                )
                            }
                        } else {
                            LoadingScreen()
                        }
                    } else {

                                ResultScreen("Test Complete",
                                    { findNavController().navigate(R.id.homeScreenFragment) },
                                    { findNavController().navigate(R.id.testFragment) }
                                )
                            }
                        }
                    }
                }
            }
        }




@Composable
fun TestContent(
    loadError: Boolean,
    question: Question?,
    onAnswerButtonClick: (String) -> Unit
) {
    if (loadError) {
        FailureLoadingQuestionsToast()
    }

    question?.let {
        Column {

            QuestionCard(questionText = question.text)
            AnswersCard(answers = question.answersList, onAnswerButtonClick = onAnswerButtonClick)
        }
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

@Composable
fun ResultScreen(
    message:String,
    navigateHome: () -> Unit,
    playAgaing: () -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Text(
                text = message,
                style = MaterialTheme.typography.body1
            )

            Button(onClick = playAgaing) {
                Text(text = stringResource(id = R.string.again_button_text))
            }

            Button(onClick = navigateHome) {
                Text(text = stringResource(id = R.string.home_button_text))
            }
        }
    }
}

