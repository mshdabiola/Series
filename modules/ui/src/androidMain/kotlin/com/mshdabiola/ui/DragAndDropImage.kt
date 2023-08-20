package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale

@Composable
actual fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    onPathChange: (String) -> Unit
) {
}

@Composable
actual fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
) {
}