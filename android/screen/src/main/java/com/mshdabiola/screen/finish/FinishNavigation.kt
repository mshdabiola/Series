package com.mshdabiola.screen.finish

import androidx.compose.runtime.Composable
import com.mshdabiola.screen.MainViewModel
import org.koin.androidx.compose.koinViewModel

@Composable
fun FinishScreenNav(
    onBack: () -> Unit,
    toQuestion: () -> Unit
) {

    val viewModel: MainViewModel = koinViewModel()
    FinishScreen(
        onBack = onBack,
        viewModel = viewModel,
        toQuestion = {
            onBack()
            toQuestion()
        }
    )
}
