package com.mshdabiola.model.data

data class Instruction(
    val id: Long? = null,
    val examId: Long,
    val title: String,
    val content: List<Content>,
)
