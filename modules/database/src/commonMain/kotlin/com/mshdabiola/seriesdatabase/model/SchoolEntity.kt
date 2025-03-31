/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(tableName = "schools")
//@TypeConverters(Converters::class) // Apply type converters at the class level or database level
data class SchoolEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "school_id")
    val schoolId: Long?, // PK, Room will auto-generate

    @ColumnInfo(name = "school_name")
    val schoolName: String,

    @ColumnInfo(name = "school_address")
    val schoolAddress: String,

    @ColumnInfo(name = "academic_year")
    val academicYear: String, // e.g., "2023-2024"
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
