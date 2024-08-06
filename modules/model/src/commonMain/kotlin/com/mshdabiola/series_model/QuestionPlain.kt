package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionPlain(
    val id: Long = -1,
    val number: Long,
    val examId: Long,
    val title: String,
    val contents: String,
    val answers: String,
    val type: Int,
    val instructionId: Long?,
    val topicId: Long?,
)
