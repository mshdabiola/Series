package com.mshdabiola.database.converter

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import com.mshdabiola.model.data.Content
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json

@ProvidedTypeConverter
class ContentListConverter {
    @TypeConverter
    fun toList(string: String): List<ContentSer> {
        return json.decodeFromString<List<ContentSer>>(string)

    }

    @TypeConverter
    fun toString(list: List<ContentSer>): String {
        val serList = list
        return json.encodeToString(serList)

    }
}