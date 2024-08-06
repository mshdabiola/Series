/*
 *abiola 2024
 */

package com.mshdabiola.series_database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.series_database.model.InstructionEntity
import com.mshdabiola.series_database.model.OptionEntity
import com.mshdabiola.series_database.model.QuestionEntity
import com.mshdabiola.series_database.model.TopicEntity

data class QuestionWithOptsInstTopRelation(
    @Embedded val questionEntity: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val options: List<OptionEntity>,
    @Relation(parentColumn = "instructionId", entityColumn = "id")
    val instructionEntity: InstructionEntity?,
    @Relation(entity = TopicEntity::class, parentColumn = "topicId", entityColumn = "id")
    val topicWithCategoryRelation: TopicWithCategoryRelation?,
)
