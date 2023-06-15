package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class QuestionUiState(
    val id: Long = -1,
    val nos: Long,
    val content: ImmutableList<ItemUi>,
    val options: ImmutableList<OptionUiState>,
    val isTheory: Boolean = false,
    val answer: String? = null,
    val instructionUiState: InstructionUiState? = null,
    val topicUiState: TopicUiState? = null

)