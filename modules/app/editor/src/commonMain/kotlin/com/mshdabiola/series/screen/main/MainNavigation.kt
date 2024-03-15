package com.mshdabiola.series.screen.main

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import com.mshdabiola.mvvn.KoinCommonViewModel

@Composable
fun MainScreenNav(
    windowSizeClass: WindowSizeClass,
    onExamClick: (Long, Long) -> Unit = { _, _ -> },
) {
    val viewModel: MainViewModel = KoinCommonViewModel()
    MainScreen(
        windowSizeClass,
        onExamClick = onExamClick,
        viewModel = viewModel,
    )
}
