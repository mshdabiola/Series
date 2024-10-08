package com.mshdabiola.seriesdatabase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SessionExamination::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sessionId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["sessionId"])],
    tableName = "paper_table",
)
data class PaperEntity(
    @PrimaryKey(true)
    val id: Long?,
    val isRandom: Boolean,
    val sessionId: Long,
    val numberQuestions: Long,
    val type: Int,
    val isGenerated: Boolean,
)
