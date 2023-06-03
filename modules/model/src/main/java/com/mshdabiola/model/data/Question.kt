package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long = -1,
    val nos: Long,
    val examId: Long,
    val content: List<Item>,
    val isTheory: Boolean,
    val answer: String?,
    val instructionId: Long?,
    val topicId: Long?
)
