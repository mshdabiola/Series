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
    tableName = "exam_papers",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete paper if course is deleted
        ),
        ForeignKey(
            entity = AcademicStaffEntity::class,
            parentColumns = ["staff_id"],
            childColumns = ["creator_staff_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Set creator to null if staff is deleted
        ),
        ForeignKey(
            entity = ExamScheduleEntity::class,
            parentColumns = ["exam_schedule_id"],
            childColumns = ["exam_schedule_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Allow exam paper even if schedule is deleted (maybe for draft papers) or change to CASCADE if paper is tightly bound to schedule
        ),
    ],
    indices = [
        Index(value = ["course_id_fk"]),
        Index(value = ["creator_staff_id_fk"]),
        Index(value = ["exam_schedule_id_fk"], unique = true), // Assuming 1 paper per schedule - make unique
    ],
)
//@TypeConverters(Converters::class)
data class ExamPaperEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "exam_paper_id")
    val examPaperId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "paper_title")
    val paperTitle: String? = null, // e.g., "Mid-Term Math Paper", optional title

    @ColumnInfo(name = "course_id_fk") // Foreign Key column name
    val courseId: Long, // FK to Course.courseId

    @ColumnInfo(name = "creator_staff_id_fk") // Foreign Key column name
    val creatorStaffId: Long, // FK to AcademicStaff.staffId

    @ColumnInfo(name = "creation_date")
    val creationDate: LocalDateTime,

    @ColumnInfo(name = "exam_schedule_id_fk", defaultValue = "NULL") // Foreign Key column name, initially nullable
    val examScheduleId: Long? = null, // FK to ExamSchedule.examScheduleId - nullable initially

    val year: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
