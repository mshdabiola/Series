/*
 *abiola 2024
 */

package com.mshdabiola.data.model

import com.mshdabiola.database.converter.toModel
import com.mshdabiola.database.converter.toSer
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.ExaminationFull
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.QuestionFull
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.TopicEntity
import com.mshdabiola.model.data.Examination
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.Option
import com.mshdabiola.model.data.Question
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic

fun Examination.asExamEntity() = ExaminationEntity(
    id = id,
    subjectId = subject.id ?: -1,
    year = year,
    isObjectiveOnly = isObjectiveOnly,
    duration = duration,
    updateTime = updateTime
)

fun ExaminationFull.asExam() = Examination(
    id = examinationEntity.id,
    year = examinationEntity.year,
    isObjectiveOnly = examinationEntity.isObjectiveOnly,
    duration = examinationEntity.duration,
    updateTime = examinationEntity.updateTime,
    subject = subjectEntity.asSub()
)

fun SubjectEntity.asSub() = Subject(
    id, title = title
)

fun Subject.asEntity() = SubjectEntity(id, title)

fun Instruction.asEntity() = InstructionEntity(id, examId, title, content.map { it.toSer() })

fun InstructionEntity.asModel() = Instruction(id, examId, title, content.map { it.toModel() })

fun Topic.asEntity() = TopicEntity(id, subjectId, title)
fun TopicEntity.asModel() = Topic(id, subjectId, title)

fun Option.asEntity() = OptionEntity(
    id = id,
    number = number,
    questionId = questionId,
    examId = examId,
    title = title,
    contents = contents.map { it.toSer() },
    isAnswer = isAnswer
)

fun OptionEntity.asModel() = Option(
    id = id,
    number = number,
    questionId = questionId,
    examId = examId,
    title = title,
    contents = contents.map { it.toModel() },
    isAnswer = isAnswer
)

fun Question.asModel() = QuestionEntity(
    id, number, examId, title, contents.map { it.toSer() }, answers.map { it.toSer() }, isTheory, instruction?.id, topic?.id
)

fun QuestionFull.asModel() = Question(
    id = questionEntity.id,
    number = questionEntity.number,
    examId = questionEntity.examId,
    title = questionEntity.title,
    contents = questionEntity.contents.map { it.toModel()},
    answers = questionEntity.answers.map { it.toModel() },
    options = options.map { it.asModel() },
    isTheory = questionEntity.isTheory,
    instruction = instructionEntity?.asModel(),
    topic = topicEntity?.asModel()
)