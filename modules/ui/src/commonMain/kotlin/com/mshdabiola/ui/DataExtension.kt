package com.mshdabiola.ui

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Item
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.SubjectUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.toImmutableList

fun QuestionFull.toQuestionUiState(isEdit: Boolean = false) = QuestionUiState(
    id = id,
    nos = nos,
    content = content.map {
        it.toItemUi(isEdit)
    }.toImmutableList(),
    options = options.map {
        it.toOptionUi(isEdit)
    }.toImmutableList(),
    isTheory = isTheory,
    answer = answer,
    instructionUiState = instruction?.toInstructionUiState(),
    topicUiState = topic?.toUi()
)

fun QuestionUiState.toQuestionWithOptions(examId: Long) = QuestionFull(
    id = id,
    nos = nos,
    examId = examId,
    content = content.map { it.toItem() },
    options = options.map {
        it.toOption(questionNos = nos, examId)
    },
    isTheory = isTheory,
    answer = answer,
    instruction = instructionUiState?.toInstruction(),
    topic = topicUiState?.toTopic()
)

fun Option.toOptionUi(isEdit: Boolean = false) =
    OptionUiState(
        id = id,
        nos = nos,
        content = content.map { it.toItemUi(isEdit) }.toImmutableList(),
        isAnswer = isAnswer
    )

fun OptionUiState.toOption(questionNos: Long, examId: Long) =
    Option(
        id = id,
        nos = nos,
        questionNos = questionNos,
        examId = examId,
        content = content.map { it.toItem() },
        isAnswer = isAnswer
    )


fun ItemUiState.toItem() = Item(content = content, type = type)
fun Item.toItemUi(isEdit: Boolean = false) =
    ItemUiState(content = content, type = type, isEditMode = isEdit)

fun InstructionUiState.toInstruction() = Instruction(
    id = id,
    examId = examId,
    title = title,
    content = content.map { it.toItem() })

fun Instruction.toInstructionUiState(isEdit: Boolean = false) =
    InstructionUiState(
        id = id,
        examId = examId,
        title = title,
        content = content.map { it.toItemUi(isEdit = isEdit) }.toImmutableList()
    )

fun Topic.toUi() = TopicUiState(id = id, subjectId = subjectId, name = name)
fun TopicUiState.toTopic() = Topic(id = id, subjectId = subjectId, name = name)

fun Subject.toUi() = SubjectUiState(id, name)
fun SubjectUiState.toSubject() = Subject(id, name)

fun ExamWithSub.toUi() = ExamUiState(id, subjectID, year, subject)
fun ExamUiState.toExam() = Exam(id, subjectID, year)