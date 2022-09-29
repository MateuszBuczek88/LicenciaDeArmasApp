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

                        LearnScreenState.ShowQuestion -> questionsLeft?.let {
                            ShowQuestion(
                                question = question,
                                onAnswerButtonClick = { chosenAnswer ->
                                    viewModel.showAnswer(
                                        chosenAnswer
                                    )
                                }, questionsLeft = it
                            )
                        }
                        LearnScreenState.ShowAnswer -> questionsLeft?.let {
                            ShowAnswers(
                                question = question!!,
                                onSurfaceClick = { viewModel.loadNextQuestion() },
                                questionsLeft = it
                            )
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
    Surface {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {

            question?.let {
                Text(
                    text = stringResource(id = R.string.questions_left, questionsLeft),
                    fontSize = 24.sp
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
}

@Composable
fun ShowAnswers(question: Question, onSurfaceClick: () -> Unit, questionsLeft: Int) {
    Surface(modifier = Modifier.clickable { onSurfaceClick() }) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(8.dp)
        ) {
            Text(
                text = stringResource(id = R.string.questions_left, questionsLeft),
                fontSize = 24.sp
            )
            Spacer(modifier = Modifier.height(20.dp))
            QuestionCard(question = question)
            Spacer(modifier = Modifier.height(20.dp))
            ShowCorrectAnswer(question = question)
        }

    }
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

        Surface(
            color = color,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier
                .fillMaxWidth(0.95f)
                .padding(1.dp), elevation = 6.dp
        ) {
            Row(
                Modifier
                    .defaultMinSize(
                        minWidth = ButtonDefaults.MinWidth,
                        minHeight = ButtonDefaults.MinHeight
                    )
                    .padding(ButtonDefaults.ContentPadding),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = it,
                    Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Left,
                    style = MaterialTheme.typography.button
                )
            }
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
