package com.mshdabiola.ui.state

data class ScoreUiState(
    val correct: Int,
    val completed: Int,
    val inCorrect: Int,
    val skipped: Int,
    val grade: Char,
)
