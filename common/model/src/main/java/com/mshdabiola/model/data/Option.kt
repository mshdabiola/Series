package com.mshdabiola.model.data

data class Option(
    val id: Long,
    val questionId: Long,
    val drawingId: Long?,
    val content: String,
    val isAnswer: Boolean
)
