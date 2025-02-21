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
import java.time.LocalDate

@Entity(
    tableName = "students",
    foreignKeys = [
        ForeignKey(
            entity = ClassEntity::class,
            parentColumns = ["class_id"],
            childColumns = ["class_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete students if class is deleted
        ),
    ],
    indices = [
        Index(value = ["class_id_fk"]),
    ],
)
@TypeConverters(Converters::class)
data class StudentEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "student_id")
    val studentId: Int = 0, // PK, Auto-generate

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "date_of_birth")
    val dateOfBirth: LocalDate,

    @ColumnInfo(name = "admission_number")
    val admissionNumber: String,

    @ColumnInfo(name = "class_id_fk") // Foreign Key column name
    val classId: Int, // FK to Class.classId
)
