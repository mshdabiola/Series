package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val id: Long = -1,
    val nos: Long,
    val questionNos: Long,
    val examId: Long,
    val content: List<Item>,
    val isAnswer: Boolean
)