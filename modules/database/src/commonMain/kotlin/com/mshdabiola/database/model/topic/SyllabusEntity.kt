package com.mshdabiola.database.model.topic

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mshdabiola.database.model.SubjectEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("subjectId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    tableName = "syllabus_table",
)
data class SyllabusEntity(
    @PrimaryKey(true)
    val id: Long?,
    val subjectId: Long,
    val title: String,
    val description: String,
)
