package com.mshdabiola.physic_series.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.app.AppNavHost
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.navigation.IRootComponent


@Composable
fun PhysicsApp(
    iRootComponent: IRootComponent
) {
    SeriesAppTheme {
        AppNavHost(iRootComponent = iRootComponent, modifier = Modifier.fillMaxSize())
    }
}
