package com.mshdabiola.seriesdatabase

import androidx.room.TypeConverter
import com.mshdabiola.seriesmodel.AttendanceStatus
import com.mshdabiola.seriesmodel.Content
import com.mshdabiola.seriesmodel.MaterialType
import com.mshdabiola.seriesmodel.QuestionType
import com.mshdabiola.seriesmodel.StaffType
import com.mshdabiola.seriesmodel.serial.asString
import com.mshdabiola.seriesmodel.serial.toContent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime


internal object DateTimeConverter{
    @TypeConverter
    fun fromLocalDateTime(dateTime: LocalDateTime): String? = dateTime.toString()

    @TypeConverter
    fun toLocalDateTime(dateTimeString: String): LocalDateTime = dateTimeString.let { LocalDateTime.parse(it) }
}

internal object TimeConverter{
    @TypeConverter
    fun fromLocalTime(time: LocalTime): String = time.toString()

    @TypeConverter
    fun toLocalTime(timeString: String): LocalTime = timeString.let { LocalTime.parse(it) }

}

internal object DateConverter{

    @TypeConverter
    fun fromLocalDate(date: LocalDate): String = date.toString()

    @TypeConverter
    fun toLocalDate(dateString: String): LocalDate = dateString.let { LocalDate.parse(it) }

}
