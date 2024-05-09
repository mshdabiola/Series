package com.mshdabiola.ui.state

data class ExamUiState(
    val id: Long = -1,
    val year: Long = 2012,
    val isObjectiveOnly: Boolean = false,
    val duration: Long = 15,
    val updateTime: Long = 56,
    val subject: SubjectUiState = SubjectUiState(name = ""),
    val isSelected: Boolean = false,
)
