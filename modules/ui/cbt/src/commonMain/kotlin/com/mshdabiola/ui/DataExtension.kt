package com.mshdabiola.ui

import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Topic
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList

fun QuestionFull.toQuestionUiState(isEdit: Boolean = false) = QuestionUiState(
    id = id,
    nos = nos,
    examId = examId,
    content = content.map {
        it.toItemUi(isEdit)
    }.toImmutableList(),
    options = options.map {
        it.toOptionUi(isEdit)
    }.toImmutableList(),
    isTheory = isTheory,
    answer = answer?.map {
        it.toItemUi(isEdit)
    }?.toImmutableList(),
    instructionUiState = instruction?.toInstructionUiState(),
    topicUiState = topic?.toUi()
)

fun Option.toOptionUi(isEdit: Boolean = false) =
    OptionUiState(
        id = id,
        nos = nos,
        content = content.map { it.toItemUi(isEdit) }.toImmutableList(),
        isAnswer = isAnswer
    )


fun Item.toItemUi(isEdit: Boolean = false) =
    ItemUiState(content = content, type = type, isEditMode = isEdit)


fun Instruction.toInstructionUiState(isEdit: Boolean = false) =
    InstructionUiState(
        id = id,
        examId = examId,
        title = title,
        content = content.map { it.toItemUi(isEdit = isEdit) }.toImmutableList()
    )

fun Topic.toUi() = TopicUiState(id = id, subjectId = subjectId, name = name)

fun ExamWithSub.toUi() = ExamUiState(
    id = id,
    subjectID = subjectID,
    year = year,
    subject = subject,
    isObjOnly = isObjOnly,
    examTime = examTime
)

