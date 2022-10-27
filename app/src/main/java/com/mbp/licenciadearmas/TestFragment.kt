package com.mbp.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.mbp.licenciadearmas.data.Question
import com.mbp.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import com.mbp.licenciadearmas.ui.theme.gunpPlay
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
                    BackgroundBox()

                    val question by viewModel.question.observeAsState()
                    val rightAnswers by viewModel.rightAnswers.observeAsState()
                    val wrongAnswers by viewModel.wrongAnswers.observeAsState()
                    val questionsLeft by viewModel.questionsLeft.observeAsState()
                    val testScreenState by viewModel.testScreenState.observeAsState()

                    when (testScreenState) {
                        TestScreenState.IsLoading -> LoadingScreen()
                        TestScreenState.LoadError -> FailureLoadingQuestionsToast()
                        TestScreenState.ShowResult -> ResultScreen(message = stringResource(
                            id = R.string.test_result_screen_message,
                            rightAnswers ?: 0,
                            wrongAnswers ?: 0
                        ),
                            { findNavController().navigate(R.id.homeScreenFragment) },
                            { findNavController().navigate(R.id.testFragment) }
                        )
                        TestScreenState.ShowQuestion -> questionsLeft?.let { _questionsLeft ->
                            TestContent(
                                questionsLeft = _questionsLeft,
                                question = question,
                                onAnswerButtonClick = { answer ->
                                    viewModel.checkAnswer(answer)
                                }
                            )
                        }
                        else -> FailureLoadingQuestionsToast()
                    }
                }
            }
        }
    }
}


@Composable
fun TestContent(
    questionsLeft: Int,
    question: Question?,
    onAnswerButtonClick: (String) -> Unit
) {

    question?.let {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.questions_left, questionsLeft),
                fontSize = 24.sp,
                fontFamily = gunpPlay,
                color = colorResource(id = R.color.logo_red)
            )
            Spacer(modifier = Modifier.height(25.dp))
            QuestionCard(questionText = question.text)
            Spacer(modifier = Modifier.height(20.dp))
            AnswersCard(
                answers = question.answersList,
                onAnswerButtonClick = onAnswerButtonClick
            )
        }
    }
}

@Composable
fun QuestionCard(questionText: String) {
    Surface(
        elevation = 12.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Text(text = questionText, modifier = Modifier.padding(8.dp), fontSize = 20.sp)
    }
}

@Composable
fun AnswersCard(answers: List<String>, onAnswerButtonClick: (String) -> Unit) {
    Column {
        answers.forEach {

            Button(
                onClick = { onAnswerButtonClick(it) },
                modifier = Modifier.fillMaxWidth(0.95f),
                elevation = ButtonDefaults.elevation(
                    defaultElevation = 6.dp,
                    pressedElevation = 8.dp,
                    disabledElevation = 0.dp
                )
            ) {
                Text(text = it, Modifier.fillMaxWidth(), textAlign = TextAlign.Left)
            }
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun ResultScreen(
    message: String,
    navigateHome: () -> Unit,
    playAgain: () -> Unit
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally, content = {
            Text(
                text = message,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                fontFamily = gunpPlay
            )
            Spacer(modifier = Modifier.height(20.dp))
            HomeScreenButton(
                text = stringResource(id = R.string.again_button_text),
                onClick = playAgain
            )
            Spacer(modifier = Modifier.height(20.dp))
            HomeScreenButton(
                text = stringResource(id = R.string.home_button_text),
                onClick = navigateHome
            )
        }
    )
}

