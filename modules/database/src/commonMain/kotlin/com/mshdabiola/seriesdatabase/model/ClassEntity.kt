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
    tableName = "classes",
    foreignKeys = [
        ForeignKey(
            entity = GradeLevelEntity::class,
            parentColumns = ["grade_level_id"],
            childColumns = ["grade_level_id_fk"],
            onDelete = ForeignKey.CASCADE, // Example: Delete classes if grade level is deleted
        ),
        ForeignKey(
            entity = AcademicStaffEntity::class,
            parentColumns = ["staff_id"],
            childColumns = ["teacher_staff_id_fk"],
            onDelete = ForeignKey.SET_NULL, // Example: Set class teacher to null if staff is deleted
        ),
    ],
    indices = [
        Index(value = ["grade_level_id_fk"]),
        Index(value = ["teacher_staff_id_fk"]),
    ],
)
@TypeConverters(Converters::class)
data class ClassEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "class_id")
    val classId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "class_name")
    val className: String, // e.g., "10A", "S5B"

    @ColumnInfo(name = "grade_level_id_fk") // Foreign Key column name in the table
    val gradeLevelId: Long, // FK to GradeLevel.gradeLevelId

    @ColumnInfo(name = "teacher_staff_id_fk") // Foreign Key column name, nullable
    val teacherStaffId: Long?, // FK to AcademicStaff.staffId (Class Teacher), nullable
)
