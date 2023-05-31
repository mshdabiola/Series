package com.mshdabiola.model.data

data class QuestionFull(
    val id: Long=-1,
    val nos: Long,
    val examId: Long,
    val content: List<Item>,
    val options: List<Option>,
    val isTheory: Boolean,
    val answer: String?,
    val instruction: Instruction?=null,
    val topicId: Long?
)

fun Question.toQuestionWithOptions(list: List<Option>,instruction: Instruction?) = QuestionFull(
    id, nos, examId, content, list, isTheory, answer, instruction, topicId
)

fun QuestionFull.toQuestion() = Question(
    id, nos, examId, content, isTheory, answer, instruction?.id, topicId
)