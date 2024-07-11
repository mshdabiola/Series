package com.mshdabiola.database.model.session

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SessionExamination::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("sessionId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    tableName = "paper")
data class Paper(
    val id: Long,
    val isRandom:Boolean,
    val sessionId: Long,
    val numberQuestions: Long,
    val type: Int,
    val isGenerated: Boolean,
)
