package com.mshdabiola.series.screen.stat

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
actual fun StatScreenPreview() {
    StatScreen(
        statState = StatState()
    )
}