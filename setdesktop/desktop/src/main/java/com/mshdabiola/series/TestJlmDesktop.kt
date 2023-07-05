package com.mshdabiola.series

//import com.himamis.retex.renderer.share.Colors
//import com.himamis.retex.renderer.share.Configuration
//import com.himamis.retex.renderer.share.TeXConstants
//import com.himamis.retex.renderer.share.TeXFormula
//import com.himamis.retex.renderer.share.platform.FactoryProvider
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.arkivanov.decompose.ExperimentalDecomposeApi
import com.arkivanov.decompose.extensions.compose.jetbrains.lifecycle.LifecycleController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.himamis.retex.renderer.desktop.LatexComponen
import javax.swing.JFrame
import javax.swing.JPanel

class TestJlmDesktop : JFrame() {
    init {
        val panel = JPanel()
        contentPane.add(panel)

        //  contentPane.add(LatexComponent(im))
        setSize(1000, 1000)
    }

    companion object {
        init {
//            if (FactoryProvider.getInstance() == null) {
//                FactoryProvider.setInstance(FactoryProviderDesktop())
//            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
//            Configuration.getFontMapping()
            val s = TestJlmDesktop()
            s.isVisible = true
        }
    }
}

@OptIn(ExperimentalDecomposeApi::class)
fun main() {

    val life = LifecycleRegistry()
    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center)
        )
        LifecycleController(life, windowState)

        Window(
            onCloseRequest = ::exitApplication,
            title = "",
            icon = painterResource("drawables/launcher/system.png"),
            state = windowState,
        ) {
            MaterialTheme {
                LazyColumn {
                    items(40) {
                        Text("name $it")
                    }
                    item { LatexComponen() }
                    item { Text("abiola") }


                }

            }
        }

    }
}