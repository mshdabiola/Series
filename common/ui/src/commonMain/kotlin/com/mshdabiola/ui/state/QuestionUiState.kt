package com.mshdabiola.ui.state

import kotlinx.collections.immutable.ImmutableList

data class QuestionUiState(
    val id: Long=-1,
    val nos: Long,
    val content: ImmutableList<ItemUi>,
    val options: ImmutableList<OptionUiState>,
    val editMode: Boolean = false,
    val isTheory: Boolean = false,
    val answer: String? = null,
    val instructionId: Long? = null,
    val topicId: Long? = null

)
