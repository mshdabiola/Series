/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters
import com.mshdabiola.seriesmodel.StaffType

@Entity(tableName = "academic_staff")
@TypeConverters(Converters::class)
data class AcademicStaffEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "staff_id")
    val staffId: Int = 0, // PK, Auto-generate

    @ColumnInfo(name = "staff_type")
    val staffType: StaffType,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "contact_details")
    val contactDetails: String,

    val password: String,
    val imagePath: String,
)
