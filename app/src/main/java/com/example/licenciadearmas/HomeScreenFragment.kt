package com.example.licenciadearmas

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.imageResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme
import com.example.licenciadearmas.ui.theme.Shapes
import com.example.licenciadearmas.ui.theme.gunpPlay

class HomeScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply {
            setContent {
                LicenciaDeArmasTheme {
                    BackgorundBox()

                    HomeScreenContent(
                        onLearnClick = { findNavController().navigate(R.id.chooseSectionFragment) },
                        onTestClick = { findNavController().navigate(R.id.testFragment) },

                        )

                }
            }
        }
    }
}

@Composable
fun HomeScreenContent(
    onLearnClick: () -> Unit,
    onTestClick: () -> Unit,


    ) {

    Surface(modifier = Modifier.fillMaxSize(), color = Color.Transparent) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
        ) {

            Spacer(modifier = Modifier.height(25.dp))


            MainText(textRes = R.string.home_screen_main_text)

            SecondaryText(textRes = R.string.home_screen_secondary_text)
            Spacer(modifier = Modifier.weight(1f))

            HomeScreenImage(imageRes = R.drawable.ic_path234)
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


@Composable
fun HomeScreenButton(text: String, onClick: () -> Unit) {
    Button(
        onClick = onClick,
        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
        shape = Shapes.small,
       elevation = ButtonDefaults.elevation(
            defaultElevation = 6.dp,
            pressedElevation = 8.dp,
           disabledElevation = 0.dp
       ),
        modifier = Modifier
            .fillMaxWidth(0.85f)
            .height(50.dp)
            .background(Brush.verticalGradient(listOf(Color.Gray,Color.White,Color.Gray)))
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.button,
            fontFamily = gunpPlay,
            color = Color.Black
        )
    }
}

@Composable
fun HomeScreenImage(imageRes: Int) {

    Image(
        imageVector = ImageVector.vectorResource(id = imageRes),
        contentDescription = "logo",
        modifier = Modifier
            .size(280.dp, 280.dp)
    )
}

@Composable
fun MainText(textRes: Int) {
    Text(
        text = stringResource(id = textRes),
        textAlign = TextAlign.Center,
        fontSize = 40.sp,
        fontFamily = gunpPlay
    )
    Spacer(modifier = Modifier.height(15.dp))
}

@Composable
fun SecondaryText(textRes: Int) {
    Text(
        text = stringResource(id = textRes),
        fontSize = 24.sp,
        color = colorResource(id = R.color.logo_red),
        fontFamily = gunpPlay
    )
}

@Composable
fun BackgorundBox(){
    Box(
        modifier = Modifier.fillMaxSize()

    )
    {
        Image(
            bitmap = ImageBitmap.imageResource(id = R.drawable.background),
            contentDescription = "",
            contentScale = ContentScale.FillBounds,
            modifier = Modifier.fillMaxSize()
        )
    }
}