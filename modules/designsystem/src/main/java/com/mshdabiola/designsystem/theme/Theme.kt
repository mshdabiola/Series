package com.mshdabiola.designsystem.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext


@Composable
fun SeriesAppTheme(
    useDarkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    darkColors: ColorScheme,
    lightColors: ColorScheme,
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (useDarkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        useDarkTheme -> darkColors
        else -> lightColors
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SeriesTypography,
        content = content,
    )
}
