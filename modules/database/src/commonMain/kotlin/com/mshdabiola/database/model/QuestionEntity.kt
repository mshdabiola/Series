/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "question_table")
data class QuestionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val number: Long,
    val examId: Long,
    val title: String,
    val contents: String,
    val answers: String,
    val isTheory: Boolean,
    val instructionId: Long?,
    val topicId: Long?,
)
