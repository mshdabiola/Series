package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable

@Composable
expect inline fun <reified T : ViewModel> KoinCommonViewModel(): T