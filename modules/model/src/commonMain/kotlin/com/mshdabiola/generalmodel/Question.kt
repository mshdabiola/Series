package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class Question(
    val id: Long = -1,
    val number: Long,
    val examId: Long,
    val title: String = "",
    val contents: List<Content>,
    val answers: List<Content>,
    val options: List<Option>?,
    val type: QUESTION_TYPE,
    val instruction: Instruction? = null,
    val topic: TopicWithCategory?,
)
