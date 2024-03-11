package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.SemanticsPropertyReceiver
import androidx.compose.ui.semantics.semantics
import org.koin.core.qualifier.Qualifier
import org.koin.core.parameter.ParametersDefinition


@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(
    key: String? ,
    qualifier: Qualifier? ,
    noinline parameters: ParametersDefinition?,
): T {
    return get(qualifier = qualifier, parameters = parameters)
}

actual fun Modifier.semanticsCommon(
    mergeDescendants: Boolean,
    properties: SemanticsPropertyReceiver.() -> Unit,
): Modifier {
    return this.semantics(mergeDescendants, properties)
}
