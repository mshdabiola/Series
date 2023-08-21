package com.mshdabiola.ui

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal actual fun FinishCardPreview() {
    FinishCard(imageVector = Icons.Default.WineBar, grade = 'E')
}

@Preview
@Composable
internal actual fun ScoreCardPreview() {
    //ScoreCard()
}