package com.mshdabiola.ui

import android.app.Activity
import androidx.compose.material3.Button
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.play.core.appupdate.AppUpdateManagerFactory
import com.google.android.play.core.appupdate.AppUpdateOptions
import com.google.android.play.core.install.InstallStateUpdatedListener
import com.google.android.play.core.install.model.AppUpdateType
import com.google.android.play.core.install.model.InstallStatus
import com.google.android.play.core.install.model.UpdateAvailability

@Composable
actual fun UpdateAppUi(modifier: Modifier) {
    var show by remember {
        mutableStateOf(false)
    }
    val activity = LocalContext.current as Activity

    val appUpdateInfoManager = remember {
        AppUpdateManagerFactory.create(activity)

    }
    var installStateUpdatedListener: InstallStateUpdatedListener? = null

    val defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)

            val appUpdateInfoTask = appUpdateInfoManager.appUpdateInfo
            appUpdateInfoTask.addOnSuccessListener { appUpdateInfo ->
                if (appUpdateInfo.updateAvailability() == UpdateAvailability.UPDATE_AVAILABLE &&
                    appUpdateInfo.isUpdateTypeAllowed(AppUpdateType.FLEXIBLE)
                ) {

                    installStateUpdatedListener = InstallStateUpdatedListener { state ->

//                    if (state.installStatus() == InstallStatus.DOWNLOADING) {
//                        val bytesDownloaded = state.bytesDownloaded()
//                        val totalBytesToDownload = state.totalBytesToDownload()
//                        // Show update progress bar.
//                    }
                        if (state.installStatus() == InstallStatus.DOWNLOADED) {
                            show = true
                        }
                    }

                    installStateUpdatedListener?.let { appUpdateInfoManager.registerListener(it) }


                    appUpdateInfoManager.startUpdateFlowForResult(
                        appUpdateInfo,

                        activity,
                        AppUpdateOptions.defaultOptions(AppUpdateType.FLEXIBLE),
                        343

                    )

                }
                //  log("update ${appUpdateInfo.packageName()} ${appUpdateInfo.availableVersionCode()}",)
            }.addOnFailureListener {
                it.printStackTrace()
            }

        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
            appUpdateInfoManager
                .appUpdateInfo
                .addOnSuccessListener { appUpdateInfo ->
                    if (appUpdateInfo.installStatus() == InstallStatus.DOWNLOADED) {
                        show = true
                    }
                    if (appUpdateInfo.installStatus() == InstallStatus.INSTALLED) {
                        installStateUpdatedListener?.let {
                            appUpdateInfoManager.unregisterListener(
                                it
                            )
                        }
                    }
                }
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current


    DisposableEffect(key1 = Unit) {
        lifecycleOwner.lifecycle.addObserver(defaultLifecycleObserver)

        onDispose {
            installStateUpdatedListener = null
            lifecycleOwner.lifecycle.removeObserver(defaultLifecycleObserver)
        }

    }

    if (show) {
        Snackbar(
            modifier = modifier,
            action = {
                Button(onClick = {
                    appUpdateInfoManager.completeUpdate()
                    show = false
                }) {
                    Text(text = "Reload")
                }
            }
        ) {
            Text(text = "Waec series just download an update")
        }
    }
}