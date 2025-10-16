package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

// the owner
@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(true)
    val id: Long?,
    val name: String,
    @ColumnInfo(defaultValue = "0")
    val type: Int,
    val password: String,
    val imagePath: String,
    val points: Long,
)
