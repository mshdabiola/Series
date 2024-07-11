/*
 *abiola 2024
 */

package com.mshdabiola.database.model.exam

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.topic.TopicEntity

data class QuestionWithOptsInstTop(
    @Embedded val questionEntity: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val options: List<OptionEntity>,
    @Relation(parentColumn = "instructionId", entityColumn = "id")
    val instructionEntity: InstructionEntity?,
    @Relation(parentColumn = "topicId", entityColumn = "id")
    val topicEntity: TopicEntity?,
    )
