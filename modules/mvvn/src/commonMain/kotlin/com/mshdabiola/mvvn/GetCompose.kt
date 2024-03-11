package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver

@Composable
expect inline fun <reified T : ViewModel> KoinCommonViewModel(): T

expect fun Modifier.semanticsCommon(
    mergeDescendants: Boolean = false,
    properties: (SemanticsPropertyReceiver.() -> Unit),
): Modifier
