/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ExamPaperEntity::class,
            parentColumns = arrayOf("exam_paper_id"),
            childColumns = arrayOf("examId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["examId"])],

    tableName = "instruction_table",
)
data class ExamInstructionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val examId: Long,
    val title: String,
    val content: String,
)
