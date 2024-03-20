/*
 *abiola 2024
 */

package com.mshdabiola.designsystem.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.Contrast
import com.mshdabiola.model.ThemeBrand

@Immutable
data class ColorFamily(
    val color: Color,
    val onColor: Color,
    val colorContainer: Color,
    val onColorContainer: Color,
)

val unspecified_scheme = ColorFamily(
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
    Color.Unspecified,
)

@Composable
fun SeriesTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    themeBrand: ThemeBrand = ThemeBrand.DEFAULT,
    themeContrast: Contrast = Contrast.Normal,
    disableDynamicTheming: Boolean = true,
    content: @Composable () -> Unit,
) {
    val themeColor = when (themeBrand) {
        ThemeBrand.GREEN -> ThemeColor.GreenThemeColor(darkTheme, themeContrast)
        else -> ThemeColor.DefaultThemeColor(darkTheme, themeContrast)
    }

    val useDynamicTheme = when {
        themeBrand != ThemeBrand.DEFAULT -> false
        !disableDynamicTheming -> true
        else -> false
    }
    val colorScheme = if (useDynamicTheme && supportsDynamicTheming()) {
        getDynamicColor(darkTheme)
    } else {
        themeColor.getColorScheme()
    }

    // Composition locals
    CompositionLocalProvider(
        LocalGradientColors provides if (useDynamicTheme) {
            GradientColors(
                container = colorScheme.surfaceColorAtElevation(
                    2.dp,
                ),
            )
        } else {
            themeColor.getGradientColors()
        },
        LocalBackgroundTheme provides themeColor.getBackgroundTheme(),
        LocalTintTheme provides themeColor.getTintTheme(),
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = SkTypography,
            content = content,
        )
    }
}

expect fun supportsDynamicTheming(): Boolean

@Composable
expect fun getDynamicColor(darkTheme: Boolean): ColorScheme
