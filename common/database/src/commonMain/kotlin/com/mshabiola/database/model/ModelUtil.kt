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


fun Instruction.toEntity() = InstructionEntity(id, title, content)

fun InstructionEntity.toModel() = Instruction(id, title, content)


fun Option.toEntity() = OptionEntity(id, nos,questionId,content, if (isAnswer) 1 else 0)

fun OptionEntity.toModel() = Option(id, nos,questionId, content, isAnswer == 1L)

fun Question.toEntity() = QuestionEntity(
    id,
    nos,
    examId,
    content,
    isTheory = if (isTheory) 1 else 0,
    answer,
    instructionId,
    topicId
)

fun QuestionEntity.toModel() = Question(
    id,
    nos,
    examId,
    content,
    isTheory = isTheory == 1L,
    answer,
    instructionId,
    topicId
)

fun Subject.toEntity() = SubjectEntity(id, name)

fun SubjectEntity.toModel() = Subject(id, name)

fun Topic.toEntity() = TopicEntity(id, name)

fun TopicEntity.toModel() = Topic(id, name)

fun Exam.toEntity() = ExamEntity(id, subjectID, year)

fun ExamEntity.toModel() = Exam(id, subjectId, year)