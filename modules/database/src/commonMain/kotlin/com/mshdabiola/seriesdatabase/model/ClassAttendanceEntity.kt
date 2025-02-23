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
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Entity(
    tableName = "class_attendance",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete attendance if student is deleted
        ),
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete attendance if class is deleted
        ),
    ],
    indices = [
        Index(value = ["student_id_fk"]),
        Index(value = ["class_id_fk"]),
    ],
    primaryKeys = ["student_id_fk", "class_id_fk", "attendance_date"], // Composite PK for unique attendance record per student, class, date
)
@TypeConverters(Converters::class)
data class ClassAttendanceEntity(
    @ColumnInfo(name = "class_attendance_id")
    val classAttendanceId: Long?, // PK, Auto-generate, not part of composite key

    @ColumnInfo(name = "student_id_fk") // Foreign Key column, Part of Composite PK
    val studentId: Long, // FK to Student.studentId

    @ColumnInfo(name = "class_id_fk") // Foreign Key column, Part of Composite PK
    val classId: Long, // FK to Class.classId

    @ColumnInfo(name = "attendance_date")
    val attendanceDate: LocalDate,

    @ColumnInfo(name = "attendance_time")
    val attendanceTime: LocalTime? = null, // Optional time

    @ColumnInfo(name = "attendance_status")
    val attendanceStatus: AttendanceStatus,

    @ColumnInfo(name = "reason")
    val reason: String? = null, // Optional reason for absence/late
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
