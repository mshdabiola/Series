/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.transfer

import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.SubjectEntity
import com.mshdabiola.database.model.TopicCategoryEntity
import com.mshdabiola.database.model.TopicEntity
import com.mshdabiola.database.model.UserEntity
import com.mshdabiola.database.model.relation.CategoryWithTopicsRelation
import com.mshdabiola.database.model.relation.ExaminationWithSubjectRelation
import com.mshdabiola.database.model.relation.QuestionWithOptsInstTopRelation
import com.mshdabiola.database.model.relation.SubjectWithSeriesRelation
import com.mshdabiola.database.model.relation.TopicWithCategoryRelation
import com.mshdabiola.seriesmodel.Examination
import com.mshdabiola.seriesmodel.Instruction
import com.mshdabiola.seriesmodel.Option
import com.mshdabiola.seriesmodel.Question
import com.mshdabiola.seriesmodel.QuestionKind
import com.mshdabiola.seriesmodel.QuestionPlain
import com.mshdabiola.seriesmodel.Series
import com.mshdabiola.seriesmodel.Subject
import com.mshdabiola.seriesmodel.Topic
import com.mshdabiola.seriesmodel.TopicCategory
import com.mshdabiola.seriesmodel.TopicWithCategory
import com.mshdabiola.seriesmodel.User
import com.mshdabiola.seriesmodel.UserType
import com.mshdabiola.seriesmodel.serial.asModel
import com.mshdabiola.seriesmodel.serial.asString
import com.mshdabiola.seriesmodel.serial.toContent
import com.mshdabiola.seriesmodel.serial.toSer

fun UserEntity.asModel() = User(
    id = id!!,
    name = name,
    type = UserType.entries[type],
    password = password,
    imagePath = imagePath,
    points = points,
)

fun User.asEntity() = UserEntity(
    id = id.checkId(),
    name = name,
    type = type.ordinal,
    password = password,
    imagePath = imagePath,
    points = points,
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
    title = title,
)

fun SubjectWithSeriesRelation.asModel() = com.mshdabiola.seriesmodel.SubjectWithSeries(
    subject = subjectEntity.asModel(),
    series = seriesEntity.asModel(),
)

fun TopicCategory.asEntity() = TopicCategoryEntity(
    id = id.checkId(),
    subjectId = subjectId,
    name = name,
)

fun TopicCategoryEntity.asModel() = TopicCategory(
    id = id!!,
    subjectId = subjectId,
    name = name,
)

fun CategoryWithTopicsRelation.asModel() = topics.map {
    TopicWithCategory(
        id = it.id!!,
        topicCategory = category.asModel(),
        title = it.title,
    )
}

fun TopicWithCategoryRelation.asModel() = TopicWithCategory(
    id = topic.id!!,
    topicCategory = topicCategory.asModel(),
    title = topic.title,
)

fun Topic.asEntity() = TopicEntity(
    id = id.checkId(),
    categoryId = categoryId,
    title = title,
)

fun TopicEntity.asModel() = Topic(
    id = id!!,
    categoryId = categoryId,
    title = title,
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

fun ExaminationWithSubjectRelation.asExam() = com.mshdabiola.seriesmodel.ExaminationWithSubject(
    examination = examinationEntity.asModel(),
    series = subjectWithSeries.seriesEntity.asModel(),
    subject = subjectWithSeries.subjectEntity.asModel(),
)

fun Instruction.asEntity() =
    InstructionEntity(
        id = id.checkId(),
        examId = examId,
        title = title,
        content = content.map { it.toSer() }.asString(),
    )

fun InstructionEntity.asModel() =
    Instruction(
        id = id!!,
        examId = examId,
        title = title,
        content = content.toContent().map { it.asModel() },
    )

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

fun QuestionPlain.asEntity() = QuestionEntity(
    id = id.checkId(),
    number = number,
    examId = examId,
    title = title,
    contents = contents,
    answers = answers,
    type = type,
    instructionId = instructionId,
    topicId = topicId,
)

fun QuestionEntity.asModel() = QuestionPlain(
    id = id!!,
    number = number,
    examId = examId,
    title = title,
    contents = contents,
    answers = answers,
    type = type,
    instructionId = instructionId,
    topicId = topicId,
)

fun QuestionWithOptsInstTopRelation.asModel() = Question(
    id = questionEntity.id!!,
    number = questionEntity.number,
    examId = questionEntity.examId,
    title = questionEntity.title,
    contents = questionEntity.contents.toContent().map { it.asModel() },
    answers = questionEntity.answers.toContent().map { it.asModel() },
    options = options.map { it.asModel() },
    type = QuestionKind.entries[questionEntity.type],
    instruction = instructionEntity?.asModel(),
    topic = topicWithCategoryRelation?.asModel(),
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

fun Long.checkId() = if (this < 0) null else this

val generalPath = ""
