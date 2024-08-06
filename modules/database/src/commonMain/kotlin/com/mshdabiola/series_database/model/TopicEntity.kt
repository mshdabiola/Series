/*
 *abiola 2024
 */

package com.mshdabiola.series_database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = TopicCategoryEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("categoryId"),
            onDelete = ForeignKey.SET_DEFAULT,
        ),
    ],
    indices = [Index(value = ["categoryId"])],
    tableName = "topic_table",
)
data class TopicEntity(
    @PrimaryKey(true)
    val id: Long?,
    @ColumnInfo(defaultValue = "1")
    val categoryId: Long, // remove syllabusId
    val title: String,
)
