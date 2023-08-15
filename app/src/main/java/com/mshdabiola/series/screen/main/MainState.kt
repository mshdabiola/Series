package com.mshdabiola.series.screen.main

import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.ScoreUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

data class MainState(
    val title: String = "Title",
    val currentExam: ExamUiState? = null,
    val listOfAllExams: ImmutableList<ExamUiState>,
    val currentPaper: Int = 0,
    val questions: ImmutableList<ImmutableList<QuestionUiState>> =
        listOf(emptyList<QuestionUiState>().toImmutableList()).toImmutableList(),
    val choose: ImmutableList<ImmutableList<Int>> =
        listOf(emptyList<Int>().toImmutableList()).toImmutableList(),
    val score: ScoreUiState? = null
)
