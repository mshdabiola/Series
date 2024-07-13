/*
 *abiola 2024
 */

package com.mshdabiola.database.model

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
    tableName = "topic_table")
data class TopicEntity(
    @PrimaryKey(true)
    val id: Long?,
    val subjectId: Long,      //remove syllabusId
    val title: String,
)
