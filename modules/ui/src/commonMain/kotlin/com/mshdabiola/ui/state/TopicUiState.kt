package com.mshdabiola.ui.state

data class TopicUiState(
    val id: Long = -1,
    val subjectId: Long,
    val name: String,
    val focus: Boolean = false,
)
