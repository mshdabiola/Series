package com.mshdabiola.mvvn

import androidx.compose.runtime.Composable

@Composable
actual inline fun <reified T : ViewModel> KoinCommonViewModel(): T {
    return getCommonViewModel()
}