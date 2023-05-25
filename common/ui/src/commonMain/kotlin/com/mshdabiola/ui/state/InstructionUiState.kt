package com.mshdabiola.ui.state

data class InstructionUiState(
    val id: Long=-1,
    val examId:Long,
    val title: String?,
    val content: List<ItemUi>
)
