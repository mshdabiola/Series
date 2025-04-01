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
    tableName = "courses",
    foreignKeys = [
        ForeignKey(
            entity = GradeLevelEntity::class,
            parentColumns = ["grade_level_id"],
            childColumns = ["grade_level_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete courses if grade level is deleted
        ),
    ],
    indices = [
        Index(value = ["grade_level_id_fk"]),
    ],
)
// @TypeConverters(Converters::class)
data class CourseEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "course_id")
    val courseId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "course_name")
    val courseName: String, // e.g., "Mathematics", "Science"

    @ColumnInfo(name = "course_code")
    val courseCode: String, // e.g., "MATH101", "SCI-G5"

    @ColumnInfo(name = "grade_level_id_fk") // Foreign Key column name
    val gradeLevelId: Long, // FK to GradeLevel.gradeLevelId
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
