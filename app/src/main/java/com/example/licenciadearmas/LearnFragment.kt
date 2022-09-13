package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material.Button
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
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
        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    val question: Question? by viewModel.question.observeAsState()

                    question?.let {
                        LearnContent(question = it,
                            onNextClick = { viewModel.nextQuestion() },
                            onPrevClick = { viewModel.prevQuestion() })
                    }
                }
            }
        }
    }
}

@Composable
fun LearnContent(question: Question, onNextClick: () -> Unit, onPrevClick: () -> Unit) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        QuestionCard(text = question.text)
        Button(onClick = onNextClick) {
            Text(text = "Next")
        }

        Button(onClick = onPrevClick) {
            Text(text = "prev")
        }
    }
}

@Composable
fun QuestionCard(text: String) {
    Surface {
        Text(text = text)
    }
}
