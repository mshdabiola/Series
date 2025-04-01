/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import com.mshdabiola.seriesmodel.Content
import kotlinx.datetime.LocalDateTime

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
    @androidx.room.PrimaryKey(true)
    val id: Long?,
    @ColumnInfo(name = "examId")
    val examId: Long,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "content")
    val content: String,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
