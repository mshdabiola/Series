package com.mshdabiola.ui.state

data class ExamUiState(
    val id: Long = -1,
    val subjectID: Long,
    val year: Long,
    val subject: String,
    val isObjOnly: Boolean,
)
