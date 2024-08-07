package com.mshdabiola.seriesdatabase.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.seriesdatabase.model.TopicCategoryEntity
import com.mshdabiola.seriesdatabase.model.TopicEntity

data class CategoryWithTopicsRelation(
    @Embedded val category: TopicCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
    )
    val topics: List<TopicEntity>,
)
