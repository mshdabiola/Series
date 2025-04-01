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
    tableName = "student_answers",
    foreignKeys = [
        ForeignKey(
            entity = StudentAnswerSheetEntity::class,
            parentColumns = ["answer_sheet_id"],
            childColumns = ["answer_sheet_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete answers if answer sheet is deleted
        ),
        ForeignKey(
            entity = ExamQuestionEntity::class,
            parentColumns = ["question_id"],
            childColumns = ["exam_question_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete answers if question is deleted
        ),
        ForeignKey(
            entity = ChoiceOptionEntity::class,
            parentColumns = ["option_id"],
            childColumns = ["choice_option_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Optional option link - allow answer even if option is deleted, set option link to null
        ),
    ],
    indices = [
        Index(value = ["answer_sheet_id_fk"]),
        Index(value = ["exam_question_id_fk"]),
        Index(value = ["choice_option_id_fk"]),
    ],
)
//@TypeConverters(Converters::class)
data class StudentAnswerEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_answer_id")
    val studentAnswerId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "answer_sheet_id_fk") // Foreign Key column name
    val answerSheetId: Long, // FK to StudentAnswerSheet.answerSheetId

    @ColumnInfo(name = "exam_question_id_fk") // Foreign Key column name
    val examQuestionId: Long, // FK to ExamQuestion.questionId

    @ColumnInfo(name = "answer_text")
    val answerText: String? = null, // For written answers, nullable

    @ColumnInfo(name = "choice_option_id_fk", defaultValue = "NULL") // Foreign Key column name, optional
    val choiceOptionId: Long? = null, // FK to ChoiceOption.optionId for MCQ, nullable

    @ColumnInfo(name = "is_correct")
    val isCorrect: Boolean? = null, // Calculated, nullable initially

    @ColumnInfo(name = "marks_obtained")
    val marksObtained: Long? = null, // Evaluated, nullable initially
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
