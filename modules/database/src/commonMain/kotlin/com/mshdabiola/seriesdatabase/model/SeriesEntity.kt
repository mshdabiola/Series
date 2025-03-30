package com.mshdabiola.seriesdatabase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.model.topic.Abiola
import com.mshdabiola.seriesdatabase.util.ConverterAbiola

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("userId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["userId"])],

    tableName = "series_table",
)
//@TypeConverters(ConverterAbiola::class)
data class SeriesEntity(
    @PrimaryKey(true)
    val id: Long?,
    val userId: Long,
    val name: String,
    val abiola: Abiola,
)
