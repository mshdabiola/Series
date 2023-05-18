package com.mshdabiola.series


import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mshdabiola.series.di.desktopModule
import com.mshdabiola.series.model.AppArgs
import com.mshdabiola.series.nav.DefaultRootComp
import com.mshdabiola.series.nav.RootComp
import org.koin.core.context.GlobalContext.startKoin

//import com.toxicbakery.logging.Arbor
//import com.toxicbakery.logging.Seedling

@OptIn(ExperimentalDecomposeApi::class)
fun mainApp(appArgs: AppArgs) {

    val life = LifecycleRegistry()
    application {
        val rootComp = DefaultRootComp(DefaultComponentContext(life))
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Floating,
            position = WindowPosition.Aligned(Alignment.Center)
        )
        LifecycleController(life, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "${appArgs.appName} (${appArgs.version})",
            icon = painterResource("drawables/launcher/system.png"),
            state = windowState,
        ) {
            MaterialTheme {
                // Igniting navigation
                RootComp(rootComp, modifier = Modifier)
            }
        }

    }
}

fun main() {

    startKoin {
        modules(desktopModule)
    }

    val appArgs = AppArgs(
        appName = "My App", // To show on title bar
        version = "v1.0.0", // To show on title inside brackets
        versionCode = 100 // To compare with latest version code (in case if you want to prompt update)
    )
//    FactoryProvider.instance = FactoryProviderAndroid()
//    TeXFormula("latex")

    mainApp(appArgs)
}