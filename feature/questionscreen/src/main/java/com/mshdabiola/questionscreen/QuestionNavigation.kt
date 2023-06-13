package com.mshdabiola.questionscreen

import androidx.compose.runtime.Composable

@Composable
fun QuestionScreenNav(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    QuestionScreen(onBack, onFinish)
}

