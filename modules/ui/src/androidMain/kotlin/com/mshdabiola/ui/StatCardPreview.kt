package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
internal actual fun StatCardPreview() {
    StartCard()
}
@Preview
@Composable
internal actual fun ContinueCardPreview() {
    ContinueCard()
}

@Preview
@Composable
internal actual fun OtherCardPreview() {
    OtherCard("OTherr", painter = painterResource(R.drawable.aaa))
}