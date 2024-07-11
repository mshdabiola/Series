package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "series_table")
data class SeriesEntity(
    @PrimaryKey(true)
    val id: Long?,
    val userId: Long,
    val name: String,
)