package com.mshdabiola.ui

import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.QuestionWithOptions
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUi
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList

fun QuestionUiState.toQuestion(examId: Long) = Question(
    id = id,
    nos = nos,
    examId = examId,
    content = content.map {
        it.toItem()
    },
    isTheory = isTheory, answer = answer, instructionId = instructionId, topicId = topicId
)

fun QuestionWithOptions.toQuestionUiState() = QuestionUiState(
    id = id,
    nos = nos,
    content = content.map {
        it.toItemUi()
    }.toImmutableList(),
    options = options.map {
        it.toOptionUi()
    }.toImmutableList(),
    isTheory = isTheory, answer = answer, instructionId = instructionId, topicId = topicId
)

fun QuestionUiState.toQuestionWithOptions(examId: Long) = QuestionWithOptions(
    id = id,
    nos = nos,
    examId = examId,
    content = content.map { it.toItem() },
    options = options.map {
        it.toOption(questionNos = nos,examId)
    },

    isTheory = isTheory, answer = answer, instructionId = instructionId, topicId = topicId
)

fun Option.toOptionUi() =
    OptionUiState(
        id = id,
        nos = nos,
        content = content.map { it.toItemUi() }.toImmutableList(),
        isAnswer = isAnswer
    )

fun OptionUiState.toOption(questionNos: Long,examId: Long) =
    Option(
        id = id,
        nos = nos,
        questionNos = questionNos,
        examId=examId,
        content = content.map { it.toItem() },
        isAnswer = isAnswer
    )


fun ItemUi.toItem() = Item(content = content, type = type)
fun Item.toItemUi() = ItemUi(content = content, type = type)

fun InstructionUiState.toInstruction()=InstructionUiState(id, examId, title, content)
fun Instruction.toInstructionUiState()=Instruction(id, examId, title, content)
