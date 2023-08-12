package com.mshdabiola.series.nav

import androidx.compose.runtime.Composable

sealed interface ChildComponent {
    @Composable
    fun render()
}
