package com.mshdabiola.series


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
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
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.series.di.desktopModule
import com.mshdabiola.series.model.AppArgs
import com.mshdabiola.series.nav.DefaultRootComp
import com.mshdabiola.series.nav.RootComp
import org.koin.core.context.GlobalContext.startKoin
import java.util.prefs.Preferences

//import com.toxicbakery.logging.Arbor
//import com.toxicbakery.logging.Seedling

@OptIn(ExperimentalDecomposeApi::class)
fun mainApp(appArgs: AppArgs) {

    val life = LifecycleRegistry()
    val preference = Preferences.userRoot()//.node("main")
    val isLightKey = "isLight"




    application {
        val rootComp = DefaultRootComp(DefaultComponentContext(life))
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center)
        )
        var isLight by remember { mutableStateOf(preference.getBoolean(isLightKey, false)) }

        LifecycleController(life, windowState)

        //   var isOpen by remember { mutableStateOf(true) }

//        if (isOpen){
//            val trayState= rememberTrayState()
//            val note= rememberNotification("notificatin","Messager")
//            Tray(state = trayState, icon = painterResource("drawables/logo.png")){
//                Item("Theme"){
//
//                }
//                Item("Notification"){
//                    trayState.sendNotification(note)
//                }
//            }
//        }


        Window(
            onCloseRequest = ::exitApplication,
            title = "${appArgs.appName} (${appArgs.version})",
            icon = painterResource("drawables/launcher/system.png"),
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
            SeriesAppTheme(!isLight) {
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
        appName = "Series", // To show on title bar
        version = "v1.0.0", // To show on title inside brackets
        versionCode = 1// To compare with latest version code (in case if you want to prompt update)
    )
//    FactoryProvider.instance = FactoryProviderAndroid()
//    TeXFormula("latex")

    mainApp(appArgs)
}