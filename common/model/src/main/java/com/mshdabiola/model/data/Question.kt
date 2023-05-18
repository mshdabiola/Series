package com.mshdabiola.model.data

data class Question(
    val id: Long,
    val examId: Long,
    val content: String,
    val isTheory: Boolean,
    val answer: String?,
    val instructionId: Long?,
    val drawingId: Long?,
    val topicId: Long?

)
