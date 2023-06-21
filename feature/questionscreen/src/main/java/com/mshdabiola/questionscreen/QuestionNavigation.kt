package com.mshdabiola.questionscreen

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.rememberSaveable
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
fun QuestionScreenNav(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val name = rememberSaveable {
        "rem${Random.nextLong()}"
    }
    val viewModel: QuestionViewModel = koinViewModel(key = name)
    QuestionScreen(onBack, onFinish, viewModel)
}

