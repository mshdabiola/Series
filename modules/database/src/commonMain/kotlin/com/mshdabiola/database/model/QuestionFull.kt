/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class QuestionFull(
    @Embedded val questionEntity: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val options: List<OptionEntity>,
    @Relation(parentColumn = "instructionId", entityColumn = "id")
    val instructionEntity: InstructionEntity?,
    @Relation(parentColumn = "topicId", entityColumn = "id")
    val topicEntity: TopicEntity?,

)
