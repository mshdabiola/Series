package com.mshdabiola.mainscreen

import androidx.compose.runtime.Composable

@Composable
fun MainScreenNav(onQuestion: () -> Unit) {
    MainScreen {
        onQuestion()
    }
}

