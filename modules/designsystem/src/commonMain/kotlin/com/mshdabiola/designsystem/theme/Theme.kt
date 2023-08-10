package com.mshdabiola.designsystem.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import com.mshdabiola.designsystem.theme.util.colorScheme


@Composable
fun SeriesAppTheme(
    isDarkMode:Boolean=false,
    content: @Composable () -> Unit,
) {
    val colorScheme = colorScheme(isDarkMode)

    MaterialTheme(
        colorScheme = colorScheme,
        typography = SeriesTypography,
        content = content,
    )
}
