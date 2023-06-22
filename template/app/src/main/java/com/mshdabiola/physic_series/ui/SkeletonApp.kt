package com.mshdabiola.physic_series.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.screen.navigation.AppNavHost


@Composable
fun PhysicsApp(
    iRootComponent: IRootComponent
) {
    SeriesAppTheme {
        AppNavHost(iRootComponent = iRootComponent, modifier = Modifier.fillMaxSize())
    }
}
