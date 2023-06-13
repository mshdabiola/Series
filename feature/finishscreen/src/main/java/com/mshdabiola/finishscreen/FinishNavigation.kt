package com.mshdabiola.finishscreen

import androidx.compose.runtime.Composable

@Composable
fun FinishScreenNav(onBack: () -> Unit) {
    FinishScreen {
        onBack()
    }
}

