import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.decompose.router.stack.webhistory.DefaultWebHistoryController
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.mshdabiola.analytics.di.analyticsModule
import kotlinx.browser.document
import kotlinx.browser.window
import org.koin.core.context.startKoin
import org.koin.dsl.koinApplication

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()

    val context =DefaultComponentContext(lifecycle = lifecycle)

    startKoin {
        modules(appModule)

    }

    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        SkeletonApp(context)
    }
}
