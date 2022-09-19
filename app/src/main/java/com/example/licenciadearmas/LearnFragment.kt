package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
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
                    val isLoading by viewModel.isLoading.observeAsState()
                    val question: Question? by viewModel.question.observeAsState()
                    val showAnswer by viewModel.showAnswer.observeAsState()
                    val showCongrats by viewModel.showCongrats.observeAsState()
                    val loadError by viewModel.loadError.observeAsState()

                    if (isLoading != true) {
                        if (showCongrats != true) {

                            showAnswer?.let { showAnswer ->

                                loadError?.let { it1 ->
                                    LearnContent(
                                        loadError = it1,
                                        question = question,
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
                    } else {
                        LoadingScreen()
                    }
                }
            }
        }
    }
}

@Composable
fun LearnContent(
    loadError: Boolean,
    question: Question?,
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
        if (loadError) {
            Toast.makeText(
                LocalContext.current,
                "Failure Loading Questions",
                Toast.LENGTH_LONG
            ).show()
        }
        question?.let {
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
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

@Composable
fun LoadingScreen() {
    Surface() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(strokeWidth = 5.dp, modifier = Modifier.size(80.dp, 80.dp))
            Text(text = "Loading", style = MaterialTheme.typography.body1)
        }
    }
}