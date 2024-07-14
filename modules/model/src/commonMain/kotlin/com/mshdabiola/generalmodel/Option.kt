package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class Option(
    val id: Long = -1,
    val number: Long,
    val questionId: Long,
    val title: String,
    val contents: List<Content>,
    val isAnswer: Boolean,
)
