package com.mshdabiola.designsystem.theme.util

import androidx.compose.material3.ColorScheme
import androidx.compose.runtime.Composable


@Composable
internal expect fun colorScheme(isDarkMode: Boolean): ColorScheme
