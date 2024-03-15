package com.mshdabiola.ui

import android.app.Activity
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.google.android.gms.games.PlayGames

@Composable
actual fun PlayLogin() {
    val activity = LocalContext.current as Activity
    val playgame = PlayGames.getGamesSignInClient(activity)
    val user = PlayGames.getPlayersClient(activity)

    var isAuthenticated by remember { mutableStateOf(false) }
    var userName by remember {
        mutableStateOf("Abiola")
    }

    val defaultLifecycleObserver = object : DefaultLifecycleObserver {
        override fun onCreate(owner: LifecycleOwner) {
            super.onCreate(owner)
        }

        override fun onStart(owner: LifecycleOwner) {
            super.onStart(owner)
            playgame.isAuthenticated
                .addOnCompleteListener { authenticationResultTask ->
                    authenticationResultTask.result?.let {
                        isAuthenticated = it.isAuthenticated

                        if (isAuthenticated) {
                            user.currentPlayer.addOnCompleteListener {
                                userName = it.result.displayName
                            }
                                .addOnFailureListener {
                                    it.printStackTrace()
                                }
                        }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    isAuthenticated = false
                }
        }

        override fun onResume(owner: LifecycleOwner) {
            super.onResume(owner)
        }
    }

    val lifecycleOwner = LocalLifecycleOwner.current

    DisposableEffect(key1 = Unit) {
        lifecycleOwner.lifecycle.addObserver(defaultLifecycleObserver)

        onDispose {
            lifecycleOwner.lifecycle.removeObserver(defaultLifecycleObserver)
        }
    }

    if (isAuthenticated) {
        Text(
            "Hello $userName",
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.titleMedium,
        )
    } else {
        Button(onClick = {
            playgame.signIn()
                .addOnCompleteListener {
                    isAuthenticated = it.result.isAuthenticated
                    if (isAuthenticated) {
                        user.currentPlayer.addOnCompleteListener {
                            userName = it.result.displayName
                        }
                            .addOnFailureListener {
                                it.printStackTrace()
                            }
                    }
                }
                .addOnFailureListener {
                    it.printStackTrace()
                    isAuthenticated = false
                }
        }) {
            Text(text = "Login")
        }
    }
}
