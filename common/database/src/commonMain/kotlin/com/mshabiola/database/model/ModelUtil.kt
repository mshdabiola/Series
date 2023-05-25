package com.mshabiola.database.model

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import commshdabioladatabase.tables.ExamEntity
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import commshdabioladatabase.tables.SubjectEntity
import commshdabioladatabase.tables.TopicEntity


fun Instruction.toEntity() = InstructionEntity(id = id, title = title, content = content)

fun InstructionEntity.toModel() = Instruction(id = id, title = title, content = content)


fun Option.toEntity() = OptionEntity(
    id = id ?: 0,
    nos = nos,
    questionNos = questionNos,
    content = content,
    isAnswer = if (isAnswer) 1 else 0
)

fun OptionEntity.toModel() = Option(
    id = id,
    nos = nos,
    questionNos = questionNos,
    content = content,
    isAnswer = isAnswer == 1L
)

fun Question.toEntity() = QuestionEntity(
    id = id ?: 0,
    nos = nos,
    examId = examId,
    content = content,
    isTheory = if (isTheory) 1 else 0,
    answer = answer,
    instructionId = instructionId,
    topicId = topicId
)

fun QuestionEntity.toModel() = Question(
    id = id,
    nos = nos,
    examId = examId,
    content = content,
    isTheory = isTheory == 1L,
    answer = answer,
    instructionId = instructionId,
    topicId = topicId
)

fun Subject.toEntity() = SubjectEntity(id, name)

fun SubjectEntity.toModel() = Subject(id, name)

fun Topic.toEntity() = TopicEntity(id, name)

fun TopicEntity.toModel() = Topic(id, name)

fun Exam.toEntity() = ExamEntity(id, subjectID, year)

fun ExamEntity.toModel() = Exam(id, subjectId, year)