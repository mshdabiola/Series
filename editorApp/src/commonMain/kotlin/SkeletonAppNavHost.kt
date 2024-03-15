import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.extensions.compose.stack.Children
import com.arkivanov.decompose.extensions.compose.stack.animation.fade
import com.arkivanov.decompose.extensions.compose.stack.animation.plus
import com.arkivanov.decompose.extensions.compose.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.stack.animation.stackAnimation

@Composable
fun SkeletonAppNavHost(appState: SkAppState) {
    Children(
        stack = appState.navController.stack,
        modifier = Modifier,
        animation = stackAnimation(fade() + slide()),
    ) {
//        when (it.instance) {
//            is IRootComponent.RootScreen.MainRootScreen -> {
//               Box(modifier = Modifier.fillMaxSize()){
//                   Text("abiola")
//               }
//            }
//
//            is IRootComponent.RootScreen.DetailRootScreen -> {
//                Box(modifier = Modifier.fillMaxSize()){
//                    Text("detail")
//                }
//            }
//
//            is IRootComponent.RootScreen.SettingRootScreen -> {
//                Box(modifier = Modifier.fillMaxSize()){
//                    Text("Setting")
//                }
//            }
    }
}
