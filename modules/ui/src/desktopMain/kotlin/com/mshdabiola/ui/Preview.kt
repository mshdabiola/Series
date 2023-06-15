package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.painterResource

@Preview
@Composable
fun OtherCardPreview() {
    OtherCard("OTher", painter = painterResource("aaa.xml"))
}