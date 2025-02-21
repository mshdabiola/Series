package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters

@Entity(tableName = "grade_levels")
@TypeConverters(Converters::class)
data class GradeLevelEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "grade_level_id")
    val gradeLevelId: Int = 0, // PK, Auto-generate

    @ColumnInfo(name = "grade_name")
    val gradeName: String, // e.g., "Grade 10", "Senior 5"

    @ColumnInfo(name = "level_number")
    val levelNumber: Int, // e.g., 10, 5
)
