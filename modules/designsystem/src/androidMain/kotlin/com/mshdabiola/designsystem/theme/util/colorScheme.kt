package com.mshdabiola.designsystem.theme.util

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.mshdabiola.designsystem.theme.DarkColors
import com.mshdabiola.designsystem.theme.LightColors

@Composable
internal actual fun colorScheme(isDarkMode: Boolean): ColorScheme {
    val dynamicColor = false
    return when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (isDarkMode) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        isDarkMode -> DarkColors
        else -> LightColors
    }
}