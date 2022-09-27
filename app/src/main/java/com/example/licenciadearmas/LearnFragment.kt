package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Section
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LearnFragment : Fragment() {
    private val viewModel: LearnViewModel by viewModel { parametersOf(section) }
    private val navArgs: LearnFragmentArgs by navArgs()
    private val section: Section by lazy { navArgs.section }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    val isLoading by viewModel.isLoading.observeAsState()
                    val question: Question? by viewModel.question.observeAsState()
                    val showAnswer by viewModel.showAnswer.observeAsState()
                    val showResult by viewModel.showResult.observeAsState()
                    val loadError by viewModel.loadError.observeAsState()

                    if (isLoading != true) {
                        if (showResult != true) {

                            showAnswer?.let { showAnswer ->

                                loadError?.let { it1 ->
                                    LearnContent(
                                        loadError = it1,
                                        question = question,
                                        showButtons = showAnswer,
                                        onQuestionClick = { viewModel.showAnswer() },
                                        rightAnswer = { viewModel.rightAnswer() },
                                        wrongAnswer = { viewModel.wrongAnswer() }
                                    )
                                }
                            }

                        } else {
                            ResultScreen(
                                message = stringResource(id = R.string.learn_result_screen_message),
                                navigateHome = { findNavController().navigate(R.id.homeScreenFragment) },
                                playAgain = { findNavController().navigate(R.id.chooseSectionFragment) }
                            )
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
    onQuestionClick: () -> Unit,
    showButtons: Boolean,
    rightAnswer: () -> Unit,
    wrongAnswer: () -> Unit
) {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            if (loadError) {
                FailureLoadingQuestionsToast()
            }
            question?.let {
                QuestionCard(question = question, onQuestionClick = onQuestionClick)
                Spacer(modifier = Modifier.height(20.dp))

                if (showButtons) {
                    AnswerCard(answerText = question.rightAnswer)
                    Spacer(modifier = Modifier.height(20.dp))

                    Row(
                        horizontalArrangement = Arrangement.SpaceBetween,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(modifier = Modifier.weight(2f)) {
                            AnswerButton(
                                buttonText = stringResource(id = R.string.button_know_answer_text),
                                rightAnswer
                            )
                        }
                        Spacer(modifier = Modifier.width(20.dp))

                        Box(modifier = Modifier.weight(2f)) {
                            AnswerButton(
                                buttonText = stringResource(id = R.string.button_didnt_know_text),
                                wrongAnswer
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun QuestionCard(question: Question, onQuestionClick: () -> Unit) {
    Surface(
        modifier = Modifier.clickable(onClick = onQuestionClick),
        elevation = 12.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Text(text = question.text, modifier = Modifier.padding(8.dp), fontSize = 18.sp)
            Text(
                text = question.answersList.joinToString(separator = "\n"),
                modifier = Modifier.padding(8.dp),
                fontSize = 18.sp,
                lineHeight = 30.sp
            )
        }
    }
}

@Composable
fun AnswerCard(answerText: String) {
    Surface {
        Text(text = answerText, style = MaterialTheme.typography.body2, fontSize = 18.sp)
    }
}

@Composable
fun LoadingScreen() {
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(strokeWidth = 5.dp, modifier = Modifier.size(80.dp, 80.dp))
            Text(
                text = stringResource(id = R.string.loading_screen_message),
                fontSize = 18.sp
            )
        }
    }
}

@Composable
fun FailureLoadingQuestionsToast() {
    Toast.makeText(
        LocalContext.current,
        stringResource(id = R.string.failure_toast_message),
        Toast.LENGTH_LONG
    ).show()
}

@Composable
fun AnswerButton(buttonText: String, onClick: () -> Unit) {
    Button(
        modifier = Modifier.fillMaxWidth(),
        onClick = onClick, elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        )
    ) {
        Text(text = buttonText)
    }
}