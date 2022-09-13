package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.ComposeView
import androidx.navigation.fragment.navArgs
import com.example.licenciadearmas.data.Sections
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.parameter.parametersOf

class LearnFragment : Fragment() {
    val viewModel: LearnViewModel by viewModel { parametersOf(section) }
    val navArgs: LearnFragmentArgs by navArgs()
    val section: Sections by lazy { navArgs.section }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LearnContent()
            }
        }
    }
}

@Composable
fun LearnContent() {
}