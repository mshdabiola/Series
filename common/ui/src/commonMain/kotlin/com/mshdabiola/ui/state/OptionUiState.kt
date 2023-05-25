package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class OptionUiState(
    val id: Long? = null,
    val nos: Long,
    val content: ImmutableList<ItemUi>,
    val isAnswer: Boolean
)
