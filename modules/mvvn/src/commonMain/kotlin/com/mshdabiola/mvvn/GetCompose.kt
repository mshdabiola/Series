package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable
import org.koin.core.annotation.KoinInternalApi
import org.koin.core.parameter.ParametersDefinition
import org.koin.core.qualifier.Qualifier

@OptIn(KoinInternalApi::class)
@Composable
expect inline fun <reified T : ViewModel> KoinCommonViewModel(
    key: String? = null,
    qualifier: Qualifier? = null,
    noinline parameters: ParametersDefinition? = null,
): T