package com.mshdabiola.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class OptionSer(
    val id: Long?,
    val number: Long,
    val questionId: Long,
    val examId: Long,
    val title: String,
    val contents: List<ContentSer>,
    val isAnswer: Boolean
)
