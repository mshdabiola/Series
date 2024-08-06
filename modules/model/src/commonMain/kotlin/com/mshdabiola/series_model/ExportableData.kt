package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class ExportableData(
    val users: List<User> = emptyList(),
    val series: List<Series> = emptyList(),
    val subjects: List<Subject> = emptyList(),
    val topicCategory: List<TopicCategory> = emptyList(),
    val topics: List<Topic> = emptyList(),
    val examinations: List<Examination> = emptyList(),
    val instructions: List<Instruction> = emptyList(),
    val questions: List<QuestionPlain> = emptyList(),
    val options: List<Option> = emptyList(),
)
