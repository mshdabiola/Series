package com.mshdabiola.model.data

data class QuestionWithOptions(
    val id: Long=-1,
    val nos: Long,
    val examId: Long,
    val content: List<Item>,
    val options: List<Option>,
    val isTheory: Boolean,
    val answer: String?,
    val instructionId: Long?,
    val topicId: Long?
)

fun Question.toQuestionWithOptions(list: List<Option>) = QuestionWithOptions(
    id, nos, examId, content, list, isTheory, answer, instructionId, topicId
)

fun QuestionWithOptions.toQuestion() = Question(
    id, nos, examId, content, isTheory, answer, instructionId, topicId
)