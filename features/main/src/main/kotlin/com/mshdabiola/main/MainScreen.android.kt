package com.mshdabiola.main

import androidx.compose.runtime.Composable
import java.io.File

@Composable
actual fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit
) {
}