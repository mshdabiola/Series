package com.mshdabiola.series.ui.feature.exam.state

data class QuestionUiState(
    val id: Long?,
    val content: String,
    val options: List<OptionsUiState>
)

data class OptionsUiState(
    val id: Long,
    val content: String,
    val isAnswer: Boolean
)
