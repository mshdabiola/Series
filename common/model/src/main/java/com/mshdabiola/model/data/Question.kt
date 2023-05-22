package com.mshdabiola.model.data

import com.mshdabiola.model.Item

data class Question(
    val id: Long,
    val examId: Long,
    val content: List<Item>,
    val isTheory: Boolean,
    val answer: String?,
    val instructionId: Long?,
    val drawingId: Long?,
    val topicId: Long?

)
