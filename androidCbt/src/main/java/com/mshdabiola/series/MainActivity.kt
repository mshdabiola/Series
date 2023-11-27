package com.mshdabiola.series

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.google.android.gms.games.AchievementsClient
import com.google.android.gms.games.PlayGamesSdk
import com.google.android.gms.tasks.OnCompleteListener
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfig
import com.google.firebase.remoteconfig.ktx.remoteConfigSettings
import com.mshdabiola.series.screen.SeriesApp
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI
import timber.log.Timber

class MainActivity : ComponentActivity() {

    private val appUpdateInfoManager by lazy { AppUpdateManagerFactory.create(this) }
    private var listener: InstallStateUpdatedListener? = null
    var achievement: AchievementsClient? = null
    var analytics: FirebaseAnalytics? = null
    var remoteConfig: FirebaseRemoteConfig? = null


    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        PlayGamesSdk.initialize(this);


        val remoteConfig = Firebase.remoteConfig
        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
            minimumFetchIntervalInSeconds = 3600
        })
        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
//        remoteConfig.fetchAndActivate()
//            .addOnCompleteListener(this) { task ->
//                if (task.isSuccessful) {
//                    val updated = task.result
//
//                  val tr=  remoteConfig.getBoolean("theme")
//                  val name  =remoteConfig.getString("name")
//
//                    Timber.e("Config params updated: %s", updated)
//                    Timber.e("theme $tr name $name")
//                    Toast.makeText(this, "Fetch and activate succeeded",
//                        Toast.LENGTH_SHORT).show()
//                } else {
//                    Toast.makeText(this, "Fetch failed",
//                        Toast.LENGTH_SHORT).show()
//                }
//            }
//
//        remoteConfig.addOnConfigUpdateListener(object : ConfigUpdateListener {
//            override fun onUpdate(configUpdate : ConfigUpdate) {
//                Timber.e("Updated keys: " + configUpdate.updatedKeys);
//
//                if (configUpdate.updatedKeys.contains("name")) {
//                    remoteConfig.activate().addOnCompleteListener {
//                        Timber.e("new name ${remoteConfig.getString("name")}")
//                    }
//                }
//            }
//
//            override fun onError(error : FirebaseRemoteConfigException) {
//                Timber.e( "Config update error with code: " + error.code, error)
//            }
//        })
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                Timber.e("Fetching FCM registration token failed", task.exception)
                return@OnCompleteListener
            }

            // Get new FCM registration token
            val token = task.result

            // Log and toast
            Timber.e(token)
            //  Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
        })

        installSplashScreen()


        val root = defaultComponentContext()


        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            KoinAndroidContext() {
                SeriesApp(context = root, isDarkMode = isSystemInDarkTheme())
                // PhysicsApp(iRootComponent = root)

            }

        }
    }
}
