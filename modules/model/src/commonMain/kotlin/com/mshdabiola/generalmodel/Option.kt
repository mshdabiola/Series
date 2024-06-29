package com.mshdabiola.generalmodel

data class Option(
    val id: Long?,
    val number: Long,
    val questionId: Long,
    val examId: Long,
    val title: String,
    val contents: List<Content>,
    val isAnswer: Boolean,
)
