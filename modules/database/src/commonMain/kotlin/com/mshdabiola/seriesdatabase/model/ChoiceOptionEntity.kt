/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mshdabiola.seriesmodel.Content
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "choice_options",
    foreignKeys = [
        ForeignKey(
            entity = ExamQuestionEntity::class,
            parentColumns = ["question_id"],
            childColumns = ["exam_question_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete options if question is deleted
        ),
    ],
    indices = [
        Index(value = ["exam_question_id_fk"]),
    ],
)
//@TypeConverters(Converters::class)

data class ChoiceOptionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "option_id")
    val optionId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "exam_question_id_fk") // Foreign Key column name
    val examQuestionId: Long, // FK to ExamQuestion.questionId

    @ColumnInfo(name = "option_text")
    val optionText: String,

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean,

    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
