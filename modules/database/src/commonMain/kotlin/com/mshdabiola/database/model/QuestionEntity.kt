/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mshdabiola.model.data.Content

@Entity(tableName = "question_table")
data class QuestionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val number: Long,
    val examId: Long,
    val title: String,
    val contents: List<Content>,
    val answers: List<Content>,
    val isTheory: Boolean,
    val instructionId: Long?,
    val topicId: Long?
)
