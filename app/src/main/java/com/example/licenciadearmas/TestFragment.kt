package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.unit.sp
import org.koin.androidx.viewmodel.ext.android.viewModel

class TestFragment : Fragment() {
    val viewModel: TestViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        viewModel.getQuestions()

        return ComposeView(requireContext()).apply {
            setContent {
                val question = viewModel.question.observeAsState()
                TestContent()
                question.value?.let {
                    Text(
                        text = it.text,
                        fontSize = 16.sp,
                        color = Color.White
                    )
                }
            }
        }
    }
}

@Composable
fun TestContent() {
}