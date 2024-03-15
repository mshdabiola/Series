import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.CanvasBasedWindow
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import org.koin.core.context.startKoin

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val lifecycle = LifecycleRegistry()

    val context = DefaultComponentContext(lifecycle = lifecycle)

    startKoin {
        modules(appModule)
    }

    CanvasBasedWindow(canvasElementId = "ComposeTarget") {
        SkeletonApp(context)
    }
}
