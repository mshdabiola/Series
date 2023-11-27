package com.mshdabiola.series.screen.finish

import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.series.screen.MainViewModel

@Composable
fun FinishScreenNav(
    onBack: () -> Unit,
    toQuestion: () -> Unit,
) {

    val viewModel: MainViewModel = KoinCommonViewModel()
    FinishScreen(
        onBack = onBack,
        viewModel = viewModel,
        toQuestion = {
            onBack()
            toQuestion()
        }
    )
}

