package com.mshdabiola.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.TopicCategoryEntity
import com.mshdabiola.database.model.TopicEntity

data class TopicWithCategoryRelation(
   @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id"
    )
    val topicCategory: TopicCategoryEntity
)
