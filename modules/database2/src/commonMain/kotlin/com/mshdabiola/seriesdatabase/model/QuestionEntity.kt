/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ExaminationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("examId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["examId"])],

    tableName = "question_table",
)
data class QuestionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val number: Long,
    val examId: Long,
    val title: String,
    val contents: String,
    val answers: String,
    @ColumnInfo(defaultValue = "0")
    val type: Int,
    val instructionId: Long?,
    val topicId: Long?,
)
