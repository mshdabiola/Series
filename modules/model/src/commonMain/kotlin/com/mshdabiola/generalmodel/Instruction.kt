package com.mshdabiola.generalmodel

data class Instruction(
    val id: Long? = null,
    val examId: Long,
    val title: String,
    val content: List<Content>,
)
