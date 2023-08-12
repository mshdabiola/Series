package com.mshdabiola.series.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.series.screen.navigation.AppNavHost


@Composable
fun PhysicsApp(
    iRootComponent: IRootComponent
) {
    SeriesAppTheme(isDarkMode = isSystemInDarkTheme()) {
        AppNavHost(iRootComponent = iRootComponent, modifier = Modifier.fillMaxSize())
    }
}
