package com.mshdabiola.series.screen.main

import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.ScoreUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainState(
    val title: String = "Title",
    val currentExam: ExamUiState? = null,
    val listOfAllExams: ImmutableList<ExamUiState>,
    val chooseObj: ImmutableList<Int> = emptyList<Int>().toImmutableList(),
    val chooseThe: ImmutableList<Int> = emptyList<Int>().toImmutableList(),
    val score: ScoreUiState?=null
)
