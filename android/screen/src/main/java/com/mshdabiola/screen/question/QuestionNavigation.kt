package com.mshdabiola.screen.question

import androidx.compose.runtime.Composable
import com.mshdabiola.screen.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun QuestionScreenNav(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {

    val viewModel: MainViewModel = koinViewModel()
    QuestionScreen(onBack = onBack, onFinish = {
        onBack()
        onFinish()
    }, viewModel = viewModel)
}

