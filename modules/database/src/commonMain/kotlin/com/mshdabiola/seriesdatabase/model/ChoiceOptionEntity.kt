/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters

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
@TypeConverters(Converters::class)
data class ChoiceOptionEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "option_id")
    val optionId: Int = 0, // PK, Auto-generate

    @ColumnInfo(name = "exam_question_id_fk") // Foreign Key column name
    val examQuestionId: Int, // FK to ExamQuestion.questionId

    @ColumnInfo(name = "option_text")
    val optionText: String,

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean,

    val title: String,
    val contents: String,
)
