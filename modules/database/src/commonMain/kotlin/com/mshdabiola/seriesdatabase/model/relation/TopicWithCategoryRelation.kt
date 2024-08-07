package com.mshdabiola.seriesdatabase.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.seriesdatabase.model.TopicCategoryEntity
import com.mshdabiola.seriesdatabase.model.TopicEntity

data class TopicWithCategoryRelation(
    @Embedded val topic: TopicEntity,
    @Relation(
        parentColumn = "categoryId",
        entityColumn = "id",
    )
    val topicCategory: TopicCategoryEntity,
)
