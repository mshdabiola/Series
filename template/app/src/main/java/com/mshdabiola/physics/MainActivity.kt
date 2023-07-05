package com.mshdabiola.physics

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import androidx.work.WorkInfo
import com.arkivanov.decompose.defaultComponentContext
import com.mshdabiola.navigation.RootComponent
import com.mshdabiola.physics.ui.PhysicsApp
import com.mshdabiola.worker.Saver
import timber.log.Timber

class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3WindowSizeClassApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var show: Boolean by mutableStateOf(true)
        val splashScreen = installSplashScreen()
        Saver
            .getWorkLiveData()
            .observe(this) {
                Timber.e(it.toString())
                val work = it.getOrNull(0)
                show = work?.state == WorkInfo.State.RUNNING

            }

        splashScreen.setKeepOnScreenCondition {
            show
        }

        val root =
            RootComponent(
                componentContext = defaultComponentContext()
            )

        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)

            // A surface container using the 'background' color from the theme
            // SkeletonApp(windowSizeClass = calculateWindowSizeClass(activity = this))
            if (!show) {
                PhysicsApp(iRootComponent = root)
            }
        }
    }
}
