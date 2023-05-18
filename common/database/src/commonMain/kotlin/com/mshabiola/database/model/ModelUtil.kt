package com.mshabiola.database.model

import com.mshdabiola.model.data.Drawing
import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import commshdabioladatabase.tables.DrawingEntity
import commshdabioladatabase.tables.ExamEntity
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import commshdabioladatabase.tables.SubjectEntity
import commshdabioladatabase.tables.TopicEntity


fun Drawing.toEntity() = DrawingEntity(id, path)

fun DrawingEntity.toModel() = Drawing(id, path)


fun Instruction.toEntity() = InstructionEntity(id, title, content)

fun InstructionEntity.toModel() = Instruction(id, title, content)


fun Option.toEntity() = OptionEntity(id, questionId, drawingId, content, if (isAnswer) 1 else 0)

fun OptionEntity.toModel() = Option(id, questionId, drawingId, content, isAnswer == 1L)

fun Question.toEntity() = QuestionEntity(
    id,
    examId,
    content,
    isTheory = if (isTheory) 1 else 0,
    answer,
    instructionId,
    drawingId,
    topicId
)

fun QuestionEntity.toModel() = Question(
    id,
    examId,
    content,
    isTheory = isTheory == 1L,
    answer,
    instructionId,
    drawingId,
    topicId
)

fun Subject.toEntity() = SubjectEntity(id, name)

fun SubjectEntity.toModel() = Subject(id, name)

fun Topic.toEntity() = TopicEntity(id, name)

fun TopicEntity.toModel() = Topic(id, name)

fun Exam.toEntity() = ExamEntity(id, subjectID, year)

fun ExamEntity.toModel() = Exam(id, subjectId, year)