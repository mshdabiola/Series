package com.mshdabiola.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class QuestionSer(
    val id: Long? = null,
    val number: Long,
    val examId: Long,
    val title: String = "",
    val contentSers: List<ContentSer>,
    val answers: List<ContentSer>,
    val optionSers: List<OptionSer>?,
    val isTheory: Boolean,
    val instructionSer: InstructionSer? = null,
    val topicSer: TopicSer?,
)
