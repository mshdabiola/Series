package com.mshdabiola.series.screen.profile

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
actual fun ProfileScreenPreview() {
    ProfileScreen(
        profileState = ProfileState()
    )
}