package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(
    key: String?,
    qualifier: Qualifier?,
    noinline parameters: ParametersDefinition?
): T {
    return getCommonViewModel(
        key = key, qualifier = qualifier, parameters = parameters
    )
}