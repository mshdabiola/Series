package com.mshdabiola.series.feature.exam.state

import kotlinx.collections.immutable.ImmutableList

data class QuestionUiState(
    val id: Long?,
    val content: ImmutableList<ItemUi>,
    val options: ImmutableList<OptionsUiState>,
    val editMode : Boolean=false
)

data class OptionsUiState(
    val id: Long,
    val content: ImmutableList<ItemUi>,
    val isAnswer: Boolean
)
