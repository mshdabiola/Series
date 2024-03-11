package com.mshdabiola.series.screen.question

import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.series.screen.MainViewModel

@Composable
fun QuestionScreenNav(
    onBack: () -> Unit,
    onFinish: () -> Unit,
) {

    val viewModel: MainViewModel = KoinCommonViewModel()
    QuestionScreen(onBack = onBack, onFinish = {
        onBack()
        onFinish()
    }, viewModel = viewModel)
}

