package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ExamInstruction(
    val id: Long = -1,
    val examId: Long,
    val title: String,
    val content: List<Content>,
)
