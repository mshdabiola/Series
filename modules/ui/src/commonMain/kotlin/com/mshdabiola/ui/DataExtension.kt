package com.mshdabiola.ui

import com.mshdabiola.model.data.Content
import com.mshdabiola.model.data.Examination
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
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

fun Question.toQuestionUiState(isEdit: Boolean = false) = QuestionUiState(
    id = id.toDefault(),
    number = number,
    examId = examId,
    contents = contents.map {
        it.toItemUi(isEdit)
    }.toImmutableList(),
    options = options?.map {
        it.toOptionUi(isEdit)
    }?.toImmutableList(),
    isTheory = isTheory,
    answers = answers.map {
        it.toItemUi(isEdit)
    }?.toImmutableList(),
    instructionUiState = instruction?.toInstructionUiState(),
    topicUiState = topic?.toUi(),
)

fun QuestionUiState.toQuestionWithOptions(examId: Long) = Question(
    id = id.check(),
    number = number,
    examId = examId,
    contents = contents.map { it.toItem() },
    options = options?.map {
        it.toOption(questionId = id, examId)
    },
    isTheory = isTheory,
    answers = answers?.map { it.toItem() } ?: emptyList(),
    instruction = instructionUiState?.toInstruction(),
    topic = topicUiState?.toTopic(),
    title = ""
)

fun Option.toOptionUi(isEdit: Boolean = false) =
    OptionUiState(
        id = id.toDefault(),
        nos = number,
        content = contents.map { it.toItemUi(isEdit) }.toImmutableList(),
        isAnswer = isAnswer,
    )

fun OptionUiState.toOption(questionId: Long, examId: Long) =
    Option(
        id = id.check(),
        number = nos,
        questionId = questionId,
        examId = examId,
        contents = content.map { it.toItem() },
        isAnswer = isAnswer,
        title = ""
    )

fun ItemUiState.toItem() = Content(content = content, type = type)
fun Content.toItemUi(isEdit: Boolean = false) =
    ItemUiState(content = content, type = type, isEditMode = isEdit)

fun InstructionUiState.toInstruction() = Instruction(
    id = id.check(),
    examId = examId,
    title = title ?: "",
    content = content.map { it.toItem() },
)

fun Instruction.toInstructionUiState(isEdit: Boolean = false) =
    InstructionUiState(
        id = id.toDefault(),
        examId = examId,
        title = title,
        content = content.map { it.toItemUi(isEdit = isEdit) }.toImmutableList(),
    )

fun Topic.toUi() = TopicUiState(id = id.toDefault(), subjectId = subjectId, name = title)
fun TopicUiState.toTopic() = Topic(id = id.check(), subjectId = subjectId, title = name)

fun Subject.toUi() = SubjectUiState(id.toDefault(), title)
fun SubjectUiState.toSubject() = Subject(id.check(), name)

fun Examination.toUi() = ExamUiState(
    id = id.toDefault(),
    year,
    isObjectiveOnly = isObjectiveOnly,
    duration = duration,
    updateTime = updateTime,
    subject = subject.toUi()
)

fun ExamUiState.toExam() =
    Examination(
        id = id.check(),
        year = year,
        isObjectiveOnly = isObjectiveOnly,
        duration = duration,
        updateTime = updateTime,
        subject = subject.toSubject()
    )

fun Long.check() = if (this == -1L) null else this
fun Long?.toDefault() = this ?: -1