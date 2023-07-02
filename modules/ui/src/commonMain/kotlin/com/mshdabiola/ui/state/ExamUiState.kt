package com.mshdabiola.ui.state

data class ExamUiState(
    val id: Long = -1,
    val subjectID: Long,
    val year: Long,
    val subject: String,
    val currentTime:Long=0,
    val totalTime : Long=4,
    val isSubmit:Boolean=false,
)
