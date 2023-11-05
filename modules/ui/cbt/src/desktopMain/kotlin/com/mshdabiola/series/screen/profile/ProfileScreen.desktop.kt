package com.mshdabiola.series.screen.profile

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.runtime.Composable
@Preview
@Composable
actual fun ProfileScreenPreview() {
    ProfileScreen(
        profileState = ProfileState()
    )
}