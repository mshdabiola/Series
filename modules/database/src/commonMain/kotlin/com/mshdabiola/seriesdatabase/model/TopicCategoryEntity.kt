package com.mshdabiola.seriesdatabase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("subjectId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["subjectId"])],
    tableName = "topic_category_table",
)
data class TopicCategoryEntity(
    @PrimaryKey(true)
    val id: Long?,
    val name: String,
    val subjectId: Long,
)
