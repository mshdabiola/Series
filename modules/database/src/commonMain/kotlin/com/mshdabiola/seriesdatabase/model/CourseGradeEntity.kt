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
    tableName = "course_grades",
    foreignKeys = [
        ForeignKey(
            entity = StudentEntity::class,
            parentColumns = ["student_id"],
            childColumns = ["student_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete grade if student is deleted
        ),
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete grade if course is deleted
        ),
    ],
    indices = [
        Index(value = ["student_id_fk"]),
        Index(value = ["course_id_fk"]),
    ],
    primaryKeys = ["student_id_fk", "course_id_fk", "academic_year"], // Composite Primary Key for unique grade per student, course, year
)
@TypeConverters(Converters::class)
data class CourseGradeEntity(
    @ColumnInfo(name = "student_id_fk") // Foreign Key column, Part of Composite PK
    val studentId: Int, // FK to Student.studentId

    @ColumnInfo(name = "course_id_fk") // Foreign Key column, Part of Composite PK
    val courseId: Int, // FK to Course.courseId

    @PrimaryKey // Part of Composite PK, needs @PrimaryKey to be recognized, even in composite key
    @ColumnInfo(name = "academic_year")
    val academicYear: String, // Part of Composite PK

    @ColumnInfo(name = "grade_value")
    val gradeValue: String, // e.g., "A", "B+", "75%", "Pass"

    @ColumnInfo(name = "grading_system")
    val gradingSystem: String? = null, // Optional e.g., "Letter Grade", "Percentage"
)
