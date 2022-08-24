package com.example.licenciadearmas

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.licenciadearmas.ui.theme.LicenciaDeArmasTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LicenciaDeArmasTheme(darkTheme = false) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    Column() {
                        QuestionCard(question = questionRepo.nextQuestion())

                    }
                }
            }
        }
    }
}

val questionRepo = QuestionRepository(Questions)

@Composable
fun QuestionCard(question: Question) {
    Column() {
        Surface(shape = MaterialTheme.shapes.small, elevation = 1.dp, modifier = Modifier.padding(all = 6.dp)) {
            Text(text = question.text, style = MaterialTheme.typography.h6)
        }
        Spacer(modifier = Modifier.height(4.dp))
        question.answersList.forEach {
            Surface(shape = MaterialTheme.shapes.small, elevation = 1.dp, modifier = Modifier.fillMaxWidth().clickable {  }) {
                Text(text = it, modifier = Modifier.padding(all = 12.dp))
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewQuestion() {
    LicenciaDeArmasTheme {
        val question = Question(
            "El arma de fuego que se recarga automáticamente, después de cada disparo, efectuando varios disparos\n" +
                    "sucesivos accionando el disparador una sola vez, se denomina: ",
            listOf(
                "A).-Arma de fuego semiautomática",
                "B).-Arma de fuego de repetición",
                "C).-Arma de fuego automática"
            ),
            "C).-Arma de fuego automática"
        )

    }
}