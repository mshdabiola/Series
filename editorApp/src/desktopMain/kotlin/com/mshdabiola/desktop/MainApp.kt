package com.mshdabiola.desktop

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import java.util.prefs.Preferences


fun mainApp() {
    val preference = Preferences.userRoot() // .node("main")
    val isLightKey = "isLight"

    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        var isLight by remember { mutableStateOf(preference.getBoolean(isLightKey, false)) }

        Window(
            onCloseRequest = ::exitApplication,
            title = "oooo",
            icon = painterResource("launcher/system.png"),
            state = windowState,
        ) {


        }
    }
}

fun main() {


    mainApp()
}
