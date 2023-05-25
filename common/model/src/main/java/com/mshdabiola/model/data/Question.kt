package com.mshdabiola.model.data

data class Question(
    val id: Long?,
    val nos: Long,
    val examId: Long,
    val content: List<Item>,
    val isTheory: Boolean,
    val answer: String?,
    val instructionId: Long?,
    val topicId: Long?
)
