/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "teacher_course_qualifications",
    primaryKeys = ["teacherStaffId_fk", "courseId_fk"],
    foreignKeys = [
        ForeignKey(
            entity = AcademicStaffEntity::class, // Renamed entity reference
            parentColumns = ["staff_id"],
            childColumns = ["teacherStaffId_fk"],
            onDelete = ForeignKey.CASCADE,
        ),
        ForeignKey(
            entity = CourseEntity::class, // Renamed entity reference
            parentColumns = ["course_id"],
            childColumns = ["courseId_fk"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index("courseId_fk")],
)
data class TeacherCourseQualificationEntity( // Renamed from RoomTeacherCourseQualificationEntity
    @ColumnInfo(name = "teacherStaffId_fk") val teacherStaffId: Long,
    @ColumnInfo(name = "courseId_fk") val courseId: Long,
    @ColumnInfo(name = "qualification_date") val qualificationDate: LocalDate?,
    @ColumnInfo(name = "notes") val notes: String?,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)

// ... (Repeat this pattern for other entities, removing "Room" prefix) ...
