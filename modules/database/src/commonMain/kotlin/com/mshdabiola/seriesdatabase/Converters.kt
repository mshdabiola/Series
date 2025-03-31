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


internal object ContentConverter{
    @TypeConverter
    fun fromContent(contents: List<Content>): String = contents.asString()
    @TypeConverter
    fun toContent(contentString: String): List<Content> = contentString.toContent()
}

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

internal object AttendanceStatusConverter{

    @TypeConverter
    fun fromAttendanceStatus(value: AttendanceStatus): String = value.name

    @TypeConverter
    fun toAttendanceStatus(value: String): AttendanceStatus = AttendanceStatus.valueOf(value)

}

internal object QuestionTypeConverter{

    @TypeConverter
    fun fromQuestionType(value: QuestionType): String = value.name

    @TypeConverter
    fun toQuestionType(value: String): QuestionType = QuestionType.valueOf(value)

}

internal object StaffTypeConverter{
    @TypeConverter
    fun fromStaffType(value: StaffType): String = value.name

    @TypeConverter
    fun toStaffType(value: String): StaffType = StaffType.valueOf(value)

}

internal object MaterialTypeConverter{

    @TypeConverter
    fun fromMaterialType(value: MaterialType): String = value.name

    @TypeConverter
    fun toMaterialType(value: String): MaterialType = MaterialType.valueOf(value)

}