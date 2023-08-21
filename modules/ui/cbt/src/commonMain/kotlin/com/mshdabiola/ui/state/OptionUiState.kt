package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class OptionUiState(
    val id: Long = -1,
    val nos: Long,
    val content: ImmutableList<ItemUiState>,
    val isAnswer: Boolean,
)
