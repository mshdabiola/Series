/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "student_answer_sheets",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete answer sheet if student is deleted
        ),
        ForeignKey(
            entity = ExamScheduleEntity::class,
            parentColumns = ["exam_schedule_id"],
            childColumns = ["exam_schedule_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete answer sheet if exam schedule is deleted
        ),
    ],
    indices = [
        Index(value = ["student_id_fk"]),
        Index(value = ["exam_schedule_id_fk"]),
    ],
)
//@TypeConverters(Converters::class)
data class StudentAnswerSheetEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "answer_sheet_id")
    val answerSheetId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "student_id_fk") // Foreign Key column name
    val studentId: Long, // FK to Student.studentId

    @ColumnInfo(name = "exam_schedule_id_fk") // Foreign Key column name
    val examScheduleId: Long, // FK to ExamSchedule.examScheduleId

    @ColumnInfo(name = "submission_date")
    val submissionDate: LocalDateTime? = null, // Could be nullable
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
