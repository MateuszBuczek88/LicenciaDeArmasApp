package com.example.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.fragment.navArgs
import com.example.licenciadearmas.data.Question
import com.example.licenciadearmas.data.Section
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import com.example.licenciadearmas.ui.theme.gunpPlay
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
                    BackgroundBox()

                    val question: Question? by viewModel.question.observeAsState()
                    val questionsLeft by viewModel.questionsLeft.observeAsState()
                    val learScreenState by viewModel.learnScreenState.observeAsState()

                    when (learScreenState) {
                        LearnScreenState.IsLoading -> LoadingScreen()
                        LearnScreenState.LoadError -> FailureLoadingQuestionsToast()
                        LearnScreenState.ShowResult -> ResultScreen(
                            message = stringResource(id = R.string.learn_result_screen_message),
                            navigateHome = { findNavController().navigate(R.id.homeScreenFragment) },
                            playAgain = { findNavController().navigate(R.id.chooseSectionFragment) })

                        LearnScreenState.ShowQuestion -> questionsLeft?.let { _questionsLeft ->
                            ShowQuestion(
                                question = question,
                                onAnswerButtonClick = { chosenAnswer ->
                                    viewModel.showAnswer(
                                        chosenAnswer
                                    )
                                }, questionsLeft = _questionsLeft
                            )
                        }
                        LearnScreenState.ShowAnswer -> questionsLeft?.let {
                            question?.let { question ->
                                ShowAnswers(
                                    question = question,
                                    onSurfaceClick = { viewModel.loadNextQuestion() },
                                    questionsLeft = it
                                )
                            }
                        }
                        null -> TODO()
                    }
                }
            }
        }
    }
}

@Composable
fun ShowQuestion(
    question: Question?,
    onAnswerButtonClick: (String) -> Unit,
    questionsLeft: Int
) {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {

            question?.let {
                Text(
                    text = stringResource(id = R.string.questions_left, questionsLeft),
                    fontSize = 24.sp,
                    fontFamily = gunpPlay,
                    color = colorResource(id = R.color.logo_red)
                )
                Spacer(modifier = Modifier.height(20.dp))
                QuestionCard(question = question)
                Spacer(modifier = Modifier.height(20.dp))
                ShowPossibleAnswers(
                    question = question,
                    onAnswerButtonClick = onAnswerButtonClick
                )
            }
        }

}

@Composable
fun ShowAnswers(question: Question, onSurfaceClick: () -> Unit, questionsLeft: Int) {

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
            Spacer(modifier = Modifier.height(20.dp))
            QuestionCard(question = question)
            Spacer(modifier = Modifier.height(20.dp))
            ShowCorrectAnswer(question = question)


    }
    Surface(
        modifier = Modifier
            .fillMaxSize()

            .clickable { onSurfaceClick() }, color = Color.Transparent
    ) {}
}

@Composable
fun QuestionCard(question: Question) {
    Surface(
        elevation = 12.dp,
        shape = RoundedCornerShape(8.dp)
    ) {
        Column {
            Text(text = question.text, modifier = Modifier.padding(8.dp), fontSize = 20.sp)
        }
    }
}

@Composable
fun ShowCorrectAnswer(question: Question) {

    question.answersList.forEach {
        val color =
            if (it == question.rightAnswer) colorResource(id = R.color.logo_green) else colorResource(
                id = R.color.logo_red
            )

        Button(
            onClick = {},
            enabled = false,
            colors = ButtonDefaults.buttonColors(disabledBackgroundColor = color),
            modifier = Modifier
                .fillMaxWidth(0.95f),
            elevation = ButtonDefaults.elevation(
                defaultElevation = 6.dp,
                pressedElevation = 8.dp,
                disabledElevation = 6.dp
            )

        ) {
            Text(
                text = it,
                Modifier.fillMaxWidth(),
                textAlign = TextAlign.Left,
                style = MaterialTheme.typography.button,
                color = Color.Black
            )
        }
        Spacer(modifier = Modifier.height(8.dp))
    }
}

@Composable
fun ShowPossibleAnswers(
    question: Question,
    onAnswerButtonClick: (String) -> Unit,
) {

    Column {

    }
    question.answersList.forEach {
        Button(
            onClick = {
                onAnswerButtonClick(it)
            },
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


@Composable
fun LoadingScreen() {

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CircularProgressIndicator(strokeWidth = 10.dp, modifier = Modifier.size(100.dp, 100.dp), color = colorResource(
                id = R.color.logo_red
            ))
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = stringResource(id = R.string.loading_screen_message),
                fontSize = 18.sp,
                fontFamily = gunpPlay
            )
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
