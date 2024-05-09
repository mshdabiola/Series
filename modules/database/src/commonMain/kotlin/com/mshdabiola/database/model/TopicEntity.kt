/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "topic_table")
data class TopicEntity(
    @PrimaryKey(true)
    val id: Long?,
    val subjectId: Long,
    val title: String,
)
