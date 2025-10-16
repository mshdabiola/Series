package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("examinerId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["examinerId"])],

    tableName = "session_examination",
)
data class SessionExamination(
    @PrimaryKey(true)
    val id: Long?,
    val examinerId: Long,
    val userId: Long?,
    val generatedFromId: Long?,
    val duration: Long,
    val remainingTime: Long,
    val totalMarks: Long,
    val isCompleted: Boolean,
    val progress: Long,
    val isUploaded: Boolean,
    val showResultImmediately: Boolean,
)
