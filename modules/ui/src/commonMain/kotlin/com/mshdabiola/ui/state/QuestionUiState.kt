package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class QuestionUiState(

    val id: Long = -1,
    val number: Long,
    val examId: Long,
    val contents: ImmutableList<ItemUiState>,
    val options: ImmutableList<OptionUiState>?,
    val isTheory: Boolean = false,
    val answers: ImmutableList<ItemUiState>? = null,
    val title: String = "",
    val instructionUiState: InstructionUiState? = null,
    val topicUiState: TopicUiState? = null,

    )
