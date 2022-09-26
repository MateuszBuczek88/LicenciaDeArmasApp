package com.example.licenciadearmas

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.findNavController

import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme

import com.example.licenciadearmas.ui.theme.Shapes

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    HomeScreenContent(
                        onLearnClick = { findNavController().navigate(R.id.chooseSectionFragment) },
                        onTestClick = { findNavController().navigate(R.id.testFragment) }
                    )
                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    onLearnClick: () -> Unit,
    onTestClick: () -> Unit
) {
    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.padding(16.dp)
        ) {
            Spacer(modifier = Modifier.height(25.dp))

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                MainText(textRes = R.string.home_screen_main_text)

                SecondaryText(textRes = R.string.home_screen_secondary_text)
                Spacer(modifier = Modifier.weight(1f))

                HomeScreenImage(imageRes = R.color.primaryDarkColor)
                Spacer(modifier = Modifier.weight(1f))

                HomeScreenButton(
                    text = stringResource(id = R.string.learn_button_text),
                    onClick = onLearnClick
                )

                Spacer(modifier = Modifier.height(25.dp))

                HomeScreenButton(
                    text = stringResource(id = R.string.test_button_text),
                    onClick = onTestClick
                )

                Spacer(modifier = Modifier.height(65.dp))
            }
        }
    }
}

@Preview
@Composable
fun HomeScreenContentPreview() {
    HomeScreenContent(onLearnClick = { /*TODO*/ }, onTestClick = { /*TODO*/ })
}

@Composable
fun HomeScreenButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        shape = Shapes.small,
        elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
            disabledElevation = 0.dp
        ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(50.dp)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
        )
    }
}

@Composable
fun HomeScreenImage(imageRes: Int) {
    Image(
        painter = painterResource(id = R.drawable.crosshair),
        contentDescription = "logo",
        modifier = Modifier
            .size(200.dp, 200.dp),

        colorFilter = ColorFilter.tint(
            colorResource(id = imageRes),
        )
    )
}

@Composable
fun MainText(textRes: Int) {
    Text(text = stringResource(id = textRes), fontSize = 40.sp)
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun SecondaryText(textRes: Int) {
    Text(
        text = stringResource(id = textRes),
        fontSize = 24.sp,
        color = colorResource(id = R.color.crosshair_red)
    )
}