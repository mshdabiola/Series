/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mshdabiola.database.converter.ContentSer
import com.mshdabiola.model.data.Content

@Entity(tableName = "question_table")
data class QuestionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val number: Long,
    val examId: Long,
    val title: String,
    val contents: List<ContentSer>,
    val answers: List<ContentSer>,
    val isTheory: Boolean,
    val instructionId: Long?,
    val topicId: Long?
)
