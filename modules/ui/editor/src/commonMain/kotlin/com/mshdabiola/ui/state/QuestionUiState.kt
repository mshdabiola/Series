package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class QuestionUiState(
    val id: Long = -1,
    val nos: Long,
    val examId: Long,
    val content: ImmutableList<ItemUiState>,
    val options: ImmutableList<OptionUiState>,
    val isTheory: Boolean = false,
    val answer: ImmutableList<ItemUiState>? = null,
    val title: String = "",
    val instructionUiState: InstructionUiState? = null,
    val topicUiState: TopicUiState? = null,

)
