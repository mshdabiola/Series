package com.mshdabiola.seriesdatabase.util

import androidx.room.TypeConverter
import com.mshdabiola.seriesmodel.AttendanceStatus
import com.mshdabiola.seriesmodel.MaterialType
import com.mshdabiola.seriesmodel.StaffType
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime

object Converters {
    @TypeConverter
    @JvmStatic
    fun fromStaffType(value: StaffType): String = value.name

    @TypeConverter
    @JvmStatic
    fun toStaffType(value: String): StaffType = StaffType.valueOf(value)

    @TypeConverter
    @JvmStatic
    fun fromMaterialType(value: MaterialType): String = value.name

    @TypeConverter
    @JvmStatic
    fun toMaterialType(value: String): MaterialType = MaterialType.valueOf(value)

    @TypeConverter
    @JvmStatic
    fun fromAttendanceStatus(value: AttendanceStatus): String = value.name

    @TypeConverter
    @JvmStatic
    fun toAttendanceStatus(value: String): AttendanceStatus = AttendanceStatus.valueOf(value)

    @TypeConverter
    @JvmStatic
    fun fromLocalDate(date: LocalDate?): String? = date?.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalDate(dateString: String?): LocalDate? = dateString?.let { LocalDate.parse(it) }

    @TypeConverter
    @JvmStatic
    fun fromLocalTime(time: LocalTime?): String? = time?.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalTime(timeString: String?): LocalTime? = timeString?.let { LocalTime.parse(it) }

    @TypeConverter
    @JvmStatic
    fun fromLocalDateTime(dateTime: LocalDateTime?): String? = dateTime?.toString()

    @TypeConverter
    @JvmStatic
    fun toLocalDateTime(dateTimeString: String?): LocalDateTime? = dateTimeString?.let { LocalDateTime.parse(it) }
}
