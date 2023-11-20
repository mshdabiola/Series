package com.mshdabiola.series.editor

//import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.core.view.WindowCompat
import com.arkivanov.decompose.defaultComponentContext
import com.mshdabiola.series.SeriesApp
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.core.annotation.KoinExperimentalAPI


class MainActivity : ComponentActivity() {
//
//    private val appUpdateInfoManager by lazy { AppUpdateManagerFactory.create(this) }
//    private var listener: InstallStateUpdatedListener? = null
//    var achievement: AchievementsClient? = null
//    var analytics: FirebaseAnalytics? = null
//    var remoteConfig: FirebaseRemoteConfig? = null
//

    @OptIn(KoinExperimentalAPI::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

      //  PlayGamesSdk.initialize(this);


//        val remoteConfig = Firebase.remoteConfig
//        remoteConfig.setConfigSettingsAsync(remoteConfigSettings {
//            minimumFetchIntervalInSeconds = 3600
//        })
//        remoteConfig.setDefaultsAsync(R.xml.remote_config_defaults)
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
//        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
//            if (!task.isSuccessful) {
//                Timber.e("Fetching FCM registration token failed", task.exception)
//                return@OnCompleteListener
//            }
//
//            // Get new FCM registration token
//            val token = task.result
//
//            // Log and toast
//            Timber.e(token)
//            //  Toast.makeText(baseContext, token, Toast.LENGTH_SHORT).show()
//        })

//        installSplashScreen()


        val root =defaultComponentContext()


        setContent {
            WindowCompat.setDecorFitsSystemWindows(window, false)
            KoinAndroidContext() {
                SeriesApp(context = root, isDarkMode = isSystemInDarkTheme())
                // PhysicsApp(iRootComponent = root)

            }

        }
    }
}
