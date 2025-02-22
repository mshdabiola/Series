/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters
import com.mshdabiola.seriesmodel.AttendanceStatus

@Entity(
    tableName = "exam_attendance",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete attendance if student is deleted
        ),
        ForeignKey(
            entity = ExamScheduleEntity::class,
            parentColumns = ["exam_schedule_id"],
            childColumns = ["exam_schedule_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete attendance if exam schedule is deleted
        ),
    ],
    indices = [
        Index(value = ["student_id_fk"]),
        Index(value = ["exam_schedule_id_fk"]),
    ],
    primaryKeys = ["student_id_fk", "exam_schedule_id_fk"], // Composite PK for unique attendance record per student, exam
)
@TypeConverters(Converters::class)
data class ExamAttendanceEntity(
    @ColumnInfo(name = "exam_attendance_id")
    val examAttendanceId: Long?, // PK, Auto-generate, not part of composite key

    @ColumnInfo(name = "student_id_fk") // Foreign Key column, Part of Composite PK
    val studentId: Long, // FK to Student.studentId

    @ColumnInfo(name = "exam_schedule_id_fk") // Foreign Key column, Part of Composite PK
    val examScheduleId: Long, // FK to ExamSchedule.examScheduleId

    @ColumnInfo(name = "attendance_status")
    val attendanceStatus: AttendanceStatus,

    @ColumnInfo(name = "reason")
    val reason: String? = null, // Optional reason for absence/late
)
