package com.mshdabiola.physic_series.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.navigation.IRootComponent
import com.mshdabiola.physic_series.navigation.PhysicSeriesAppNavHost


@Composable
fun PhysicsApp(
    iRootComponent: IRootComponent
) {
    SeriesAppTheme {
        PhysicSeriesAppNavHost(IRootComponent = iRootComponent, modifier = Modifier.fillMaxSize())
    }
}
