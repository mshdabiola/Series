package com.mshdabiola.ui.state

data class InstructionUiState(
    val id: Long,
    val title: String?,
    val content: List<ItemUi>
)
