package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.findNavController

import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme

class HomeScreenFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    HomeScreenContent(
                        onLearnButtonClick = { findNavController().navigate(R.id.chooseSectionFragment) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(onLearnButtonClick: () -> Unit) {
    Surface() {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Button(onClick = onLearnButtonClick) {
                Text(text = stringResource(id = R.string.learn_button_text))
            }
            Button(onClick = { /*TODO*/ }) {
                Text(text = stringResource(id = R.string.test_button_text))
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenContetnPreview() {
    HomeScreenContent {
    }
}
