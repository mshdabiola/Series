/*
 *abiola 2024
 */

package com.mshdabiola.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.TopicEntity

data class QuestionWithOptsInstTopRelation(
    @Embedded val questionEntity: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val options: List<OptionEntity>,
    @Relation(parentColumn = "instructionId", entityColumn = "id")
    val instructionEntity: InstructionEntity?,
    @Relation(parentColumn = "topicId", entityColumn = "id")
    val topicEntity: TopicEntity?,
    )
