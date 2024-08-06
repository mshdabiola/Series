package com.mshdabiola.series_database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.series_database.model.TopicCategoryEntity
import com.mshdabiola.series_database.model.TopicEntity

data class CategoryWithTopicsRelation(
    @Embedded val category: TopicCategoryEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "categoryId",
    )
    val topics: List<TopicEntity>,
)
