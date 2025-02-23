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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Entity(
    tableName = "exam_schedules",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete schedule if class is deleted
        ),
        ForeignKey(
            entity = ExamPaperEntity::class,
            parentColumns = ["exam_paper_id"],
            childColumns = ["exam_paper_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Allow schedule even if paper is deleted (unlikely scenario - reconsider CASCADE if schedule always needs a paper) or change to CASCADE if schedule is tightly bound to paper
        ),
    ],
    indices = [
        Index(value = ["class_id_fk"]),
        Index(value = ["exam_paper_id_fk"]),
    ],
)
@TypeConverters(Converters::class)
data class ExamScheduleEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exam_schedule_id")
    val examScheduleId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "exam_name")
    val examName: String, // e.g., "Mid-Term Exam", "Unit Test 1"

    @ColumnInfo(name = "exam_date")
    val examDate: LocalDate,

    @ColumnInfo(name = "start_time")
    val startTime: LocalTime,

    @ColumnInfo(name = "end_time")
    val endTime: LocalTime,

    @ColumnInfo(name = "class_id_fk") // Foreign Key column name
    val classId: Long, // FK to Class.classId

    @ColumnInfo(name = "exam_paper_id_fk", defaultValue = "NULL") // Foreign Key column name, nullable
    val examPaperId: Long? = null, // FK to ExamPaper.examPaperId - nullable initially
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
