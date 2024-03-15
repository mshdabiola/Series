package com.mshdabiola.series.screen.main

import android.os.Environment
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.platform.LocalContext
import java.io.File

@Composable
actual fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit,
    onFile: (File?) -> Unit,
) {
    val context = LocalContext.current

    LaunchedEffect(key1 = show) {
        //  if (show){

        val file = context.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        onDismiss()
        onFile(file)
        // }
    }
}
