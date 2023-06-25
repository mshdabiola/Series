package com.mshdabiola.screen.main

import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList

data class MainState(
    val currentExam:ExamUiState?=null,
    val progress : Float=0f,
    val exams : ImmutableList<ExamUiState>
)
