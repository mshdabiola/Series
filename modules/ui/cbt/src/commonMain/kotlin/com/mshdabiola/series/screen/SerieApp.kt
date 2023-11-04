package com.mshdabiola.series.screen

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.DefaultComponentContext
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.navigation.RootComponent
import com.mshdabiola.series.screen.navigation.AppNavHost

@Composable
fun SeriesApp(context: DefaultComponentContext, isDarkMode: Boolean) {

    val rootComp = RootComponent(context)
//    val windowSizeClass =    WindowSizeClass.calculateFromSize(Size(451f,456f), LocalDensity.current)

    SeriesAppTheme(isDarkMode = isDarkMode) {
        AppNavHost(
            iRootComponent = rootComp,
            modifier = Modifier.fillMaxSize(),
        )
    }



}
