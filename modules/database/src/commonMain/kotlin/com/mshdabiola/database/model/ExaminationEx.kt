/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import com.mshdabiola.database.model.exam.ExaminationEntity
import com.mshdabiola.database.model.exam.ExaminationWithSubject
import com.mshdabiola.database.model.exam.InstructionEntity
import com.mshdabiola.database.model.exam.OptionEntity
import com.mshdabiola.database.model.exam.QuestionEntity
import com.mshdabiola.database.model.exam.QuestionWithOptsInstTop
import com.mshdabiola.database.model.exam.SubjectEntity
import com.mshdabiola.database.model.exam.SubjectWithSeries
import com.mshdabiola.database.model.topic.TopicEntity
import com.mshdabiola.generalmodel.Examination
import com.mshdabiola.generalmodel.Instruction
import com.mshdabiola.generalmodel.Option
import com.mshdabiola.generalmodel.QUESTION_TYPE
import com.mshdabiola.generalmodel.Question
import com.mshdabiola.generalmodel.Series
import com.mshdabiola.generalmodel.Subject
import com.mshdabiola.generalmodel.Topic
import com.mshdabiola.generalmodel.User
import com.mshdabiola.generalmodel.UserType
import com.mshdabiola.generalmodel.serial.asModel
import com.mshdabiola.generalmodel.serial.asString
import com.mshdabiola.generalmodel.serial.toContent
import com.mshdabiola.generalmodel.serial.toSer

fun UserEntity.asModel() = User(
    id = id!!,
    name = name,
    type = UserType.entries[type],
    password = password,
    imagePath = imagePath,
    points = points
)

fun User.asEntity() = UserEntity(
    id = id.checkId(),
    name = name,
    type = type.ordinal,
    password = password,
    imagePath = imagePath,
    points = points
)

fun SeriesEntity.asModel() = Series(
    id = id!!,
    userId = userId,
    name = name,
)

fun Series.asEntity() = SeriesEntity(
    id = id.checkId(),
    userId = userId,
    name = name,
)

fun SubjectEntity.asModel() = Subject(
    id = id!!,
    seriesId = seriesId,
    title = title,
)


fun Subject.asEntity() = SubjectEntity(
    id = id.checkId(),
    seriesId = seriesId,
    title = title
)


fun SubjectWithSeries.asModel() = com.mshdabiola.generalmodel.SubjectWithSeries(
    subject = subjectEntity.asModel(),
    series = seriesEntity.asModel(),
)


fun Topic.asEntity() = TopicEntity(
    id = id.checkId(),
    subjectId = subjectId,
    title = title
)

fun TopicEntity.asModel() = Topic(
    id = id!!,
    subjectId = subjectId,
    title = title
)


fun Examination.asEntity() = ExaminationEntity(
    id = id.checkId(),
    subjectId = subjectId,
    year = year,
    duration = duration,
)

fun ExaminationEntity.asModel() = Examination(
    id = id!!,
    subjectId = subjectId,
    year = year,
    duration = duration,
)

fun ExaminationWithSubject.asExam() = com.mshdabiola.generalmodel.ExaminationWithSubject(
    examination = examinationEntity.asModel(),
    series = subjectWithSeries.seriesEntity.asModel(),
    subject = subjectWithSeries.subjectEntity.asModel(),
)


fun Instruction.asEntity() =
    InstructionEntity(
        id = id.checkId(),
        examId = examId,
        title = title,
        content = content.map { it.toSer() }.asString()
    )

fun InstructionEntity.asModel() =
    Instruction(
        id = id!!,
        examId = examId,
        title = title,
        content = content.toContent().map { it.asModel() })


fun Question.asModel() = QuestionEntity(
    id = id.checkId(),
    number = number,
    examId = examId,
    title = title,
    contents = contents.map { it.toSer() }.asString(),
    answers = answers.map { it.toSer() }.asString(),
    type = type.ordinal,
    instructionId = instruction?.id,
    topicId = topic?.id,
)


fun QuestionWithOptsInstTop.asModel() = Question(
    id = questionEntity.id!!,
    number = questionEntity.number,
    examId = questionEntity.examId,
    title = questionEntity.title,
    contents = questionEntity.contents.toContent().map { it.asModel() },
    answers = questionEntity.answers.toContent().map { it.asModel() },
    options = options.map { it.asModel() },
    type = QUESTION_TYPE.entries[questionEntity.type],
    instruction = instructionEntity?.asModel(),
    topic = topicEntity?.asModel(),
)

fun Option.asEntity() = OptionEntity(
    id = id.checkId(),
    number = number,
    questionId = questionId,
    title = title,
    contents = contents.map { it.toSer() }.asString(),
    isAnswer = isAnswer,
)

fun OptionEntity.asModel() = Option(
    id = id!!,
    number = number,
    questionId = questionId,
    title = title,
    contents = contents.toContent().map { it.asModel() },
    isAnswer = isAnswer,
)


fun Long.checkId() = if (this == -1L) null else this