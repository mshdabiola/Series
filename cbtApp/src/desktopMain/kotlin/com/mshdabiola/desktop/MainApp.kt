package com.mshdabiola.desktop

import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.MenuBar
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mshdabiola.model.Security
import com.mshdabiola.model.canMigrate
import com.mshdabiola.model.databaseName
import com.mshdabiola.model.generalPath
import com.mshdabiola.series.di.appModule
import com.mshdabiola.series.screen.SeriesApp
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.koin.core.context.GlobalContext.startKoin
import java.io.File
import java.util.prefs.Preferences

// import com.toxicbakery.logging.Arbor
// import com.toxicbakery.logging.Seedling

@OptIn(ExperimentalDecomposeApi::class, ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalResourceApi::class
)
fun mainApp(appArgs: AppArgs) {
    val preference = Preferences.userRoot() // .node("main")
    val isLightKey = "isLight"

    val life = LifecycleRegistry()
    application {
        val defaultComponentContext = DefaultComponentContext(life)
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        LifecycleController(life, windowState)
        var isLight by remember { mutableStateOf(preference.getBoolean(isLightKey, false)) }

        Window(
            onCloseRequest = ::exitApplication,
            title = "${appArgs.appName} (${appArgs.version})",
            icon = painterResource(DrawableResource("drawable/launcher/system.png")),
            state = windowState,
        ) {
            MenuBar {
                Menu("Theme", 'T') {
                    if (!isLight) {
                        Item("Light Theme") {
                            isLight = true
                            preference.putBoolean(isLightKey, true)
                            preference.flush()
                        }
                    }
                    if (isLight) {
                        Item("Dark Theme") {
                            isLight = false
                            preference.putBoolean(isLightKey, false)
                            preference.flush()
                        }
                    }
                }
            }

            SeriesApp(
                context = defaultComponentContext,
            )
        }
    }
}

fun main() {
    startKoin {
        modules(appModule)
    }

    databaseName = "series.db"
    canMigrate = false
    val dir = File(generalPath)
    if (dir.exists().not()) {
        dir.mkdirs()
    }
    val destinationFile = File(generalPath, databaseName)
    println(destinationFile.toPath())

    if (destinationFile.exists().not()) {
        val classLoader = Thread.currentThread().contextClassLoader
        val data = classLoader.getResourceAsStream("files/data")
        val version = classLoader.getResourceAsStream("files/version.txt")
        val key = "mshdabiola20"
        Security.copy(destinationFile, version!!, data!!, key)

    }

    val appArgs = AppArgs(
        appName = "Skeleton App", // To show on title bar
        version = "v2.0.0", // To show on title inside brackets
        versionCode = 100, // To compare with latest version code (in case if you want to prompt update)
    )

    mainApp(appArgs)
}
