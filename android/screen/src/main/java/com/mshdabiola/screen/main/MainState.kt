package com.mshdabiola.screen.main

import com.mshdabiola.ui.state.ExamUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainState(
    val title:String="Title",
    val currentExam:ExamUiState?=null,
    val exams : ImmutableList<ExamUiState>,
    val choose: ImmutableList<Int> = emptyList<Int>().toImmutableList()
)
