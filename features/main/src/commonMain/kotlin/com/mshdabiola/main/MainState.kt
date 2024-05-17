package com.mshdabiola.main

import com.mshdabiola.model.Note
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

// sealed interface MainState {
//    data class Show(val models: List<ModelUiState>) : MainState
//    object Error : MainState
//
//    object Loading : MainState
// }

data class MainState(
    val name: String = "abiola",
    val currentSubjectId: Long = -1,
    val examination: ExamUiState = ExamUiState(),
    val subject: SubjectUiState = SubjectUiState(name = ""),
    val dateError: Boolean = false,
    val isSelectMode: Boolean = false,
    val examinations: ImmutableList<ExamUiState> = emptyList<ExamUiState>().toImmutableList(),
    val subjects: ImmutableList<SubjectUiState> = emptyList<SubjectUiState>().toImmutableList(),
//    val messages: ImmutableList<Notify> = emptyList<Notify>().toImmutableList()
)

