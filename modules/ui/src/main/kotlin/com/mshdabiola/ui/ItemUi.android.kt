package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import com.mshdabiola.ui.state.ItemUiState

@Composable
internal actual fun EquationUi(
    modifier: Modifier,
    equation: ItemUiState
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