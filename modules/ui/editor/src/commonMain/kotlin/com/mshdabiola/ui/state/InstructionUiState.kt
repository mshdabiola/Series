package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class InstructionUiState(
    val id: Long = -1,
    val examId: Long,
    val title: String?,
    val content: ImmutableList<ItemUiState>,
)
