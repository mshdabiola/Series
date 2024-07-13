package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PaperEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("paperId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["paperId"])],

    tableName = "session_question")
data class SessionQuestion(
    @PrimaryKey(true)
    val id: Long?,
    val paperId: Long,
    val questionId: Long,
    val chosenOptionId: Long,
    val answer: String,
    val isCorrect: Boolean,
)
