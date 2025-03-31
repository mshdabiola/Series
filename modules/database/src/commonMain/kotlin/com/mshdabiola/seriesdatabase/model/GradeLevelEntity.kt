package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "grade_levels",
    foreignKeys = [
        ForeignKey(
            entity = SchoolEntity::class,
            parentColumns = ["school_id"],
            childColumns = ["school_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["school_id"]),

    ],
)
//@TypeConverters(Converters::class)
data class GradeLevelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "grade_level_id")
    val gradeLevelId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "grade_name")
    val gradeName: String, // e.g., "Grade 10", "Senior 5"

    @ColumnInfo(name = "level_number")
    val levelNumber: Long, // e.g., 10, 5

    @ColumnInfo(name = "school_id")
    val schoolId: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
