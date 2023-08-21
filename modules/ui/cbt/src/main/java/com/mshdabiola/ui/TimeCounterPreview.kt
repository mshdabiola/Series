package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal actual fun TimeCounterPreview() {
    TimeCounter(
        currentTime2 = 150L,
        total = 300L
    )
}