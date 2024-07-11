package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user_table")
data class UserEntity(
    @PrimaryKey(true)
    val id: Long?,
    val name: String,
    val password: String,
    val imagePath: String,
    val rank :Long
)
