package com.mshdabiola.model.data

data class Question(
    val id: Long? = null,
    val number: Long,
    val examId: Long,
    val title: String = "",
    val contents: List<Content>,
    val answers: List<Content>,
    val options: List<Option>?,
    val isTheory: Boolean,
    val instruction: Instruction? = null,
    val topic: Topic?,
)
