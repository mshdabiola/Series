package com.mshdabiola.seriesdatabase.util

import androidx.room.TypeConverter
import com.mshdabiola.seriesdatabase.model.topic.Abiola

object ConverterAbiola {
    @TypeConverter
    fun toAbiola(value: String?) = enumValueOf<Abiola>(value?: Abiola.Hammi.name)
    @TypeConverter
    fun fromAbiola(value: Abiola?) = value?.name ?: Abiola.Hammi.name

}