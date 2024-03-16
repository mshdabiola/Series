package com.mshdabiola.series.screen.setting

import androidx.compose.runtime.Composable

@Composable
fun SettingScreenNav(onBack: () -> Unit) {
    SettingScreen {
        onBack()
    }
}
