package com.mshdabiola.model.data

data class Instruction(
    val id: Long=-1,
    val examId:Long,
    val title: String?,
    val content: List<Item>
)
