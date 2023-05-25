package com.mshdabiola.model.data

data class Option(
    val id: Long,
    val nos:Long,
    val questionId: Long,
    val content: List<Item>,
    val isAnswer: Boolean
)
