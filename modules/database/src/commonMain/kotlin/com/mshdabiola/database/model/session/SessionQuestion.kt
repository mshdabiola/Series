package com.mshdabiola.database.model.session

import androidx.room.Entity
import androidx.room.ForeignKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = Paper::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("paperId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    tableName = "session_question")
data class SessionQuestion(
    val id: Long,
    val paperId: Long,
    val questionId: Long,
    val chosenOptionId: Long,
    val answer: String,
    val isCorrect: Boolean,
)
