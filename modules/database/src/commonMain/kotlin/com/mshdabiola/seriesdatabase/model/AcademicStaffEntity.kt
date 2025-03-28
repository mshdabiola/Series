/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters
import com.mshdabiola.seriesmodel.StaffType
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "academic_staff",
    foreignKeys = [
        androidx.room.ForeignKey(
            entity = SchoolEntity::class,
            parentColumns = ["school_id"],
            childColumns = ["school_id"],
            onDelete = androidx.room.ForeignKey.CASCADE,
        ),
    ],
    indices = [
        Index(value = ["school_id"]),

    ],
)
//@TypeConverters(Converters::class) // Updated closing parenthesis here
data class AcademicStaffEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "staff_id")
    val staffId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "staff_type")
    val staffType: StaffType,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "contact_details")
    val contactDetails: String,

    val password: String,
    val imagePath: String,
    @ColumnInfo(name = "school_id")
    val schoolId: Long,
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
