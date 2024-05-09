package com.mshdabiola.database.converter

import androidx.room.TypeConverter
import com.mshdabiola.model.data.Content
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

val json = Json

class ContentListConverter {
    @TypeConverter
    fun toList(string: String): List<Content> {
        return json.decodeFromString<List<ContentSer>>(string)
            .map { it.toModel() }
    }

    @TypeConverter
    fun toString(list: List<Content>): String {
        val serList = list
            .map { it.toSer() }
        return json.encodeToString(serList)

    }
}