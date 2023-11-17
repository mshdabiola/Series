package com.mshdabiola.series.feature.main

import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel

@Composable
fun MainScreenNav(
    onExamClick: (Long, Long) -> Unit = { _, _ -> },
) {

    val viewModel: MainViewModel = KoinCommonViewModel()
    MainScreen(
        onExamClick = onExamClick,
        viewModel = viewModel
    )
}

