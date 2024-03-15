package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class QuestionFull(
    val id: Long = -1,
    val nos: Long,
    val examId: Long,
    val content: List<Item>,
    val options: List<Option>,
    val isTheory: Boolean,
    val answer: List<Item>?,
    val instruction: Instruction? = null,
    val topic: Topic?,
)

fun Question.toQuestionWithOptions(
    list: List<Option>,
    instruction: Instruction?,
    topic: Topic?,

) =
    QuestionFull(
        id, nos, examId, content, list, isTheory, answer, instruction, topic,
    )

fun QuestionFull.toQuestion() = Question(
    id,
    nos,
    examId,
    content,
    isTheory,
    answer,
    instruction?.id,
    topic?.id,
)
