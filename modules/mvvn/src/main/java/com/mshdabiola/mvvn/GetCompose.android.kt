package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId

@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(): T {
    return getCommonViewModel()
}

@OptIn(ExperimentalComposeUiApi::class)
actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics {
        this.testTagsAsResourceId = true
    }
}
