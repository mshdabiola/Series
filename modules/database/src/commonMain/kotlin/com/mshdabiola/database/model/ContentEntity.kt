/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mshdabiola.model.data.Type

@Entity(tableName = "examination_table")
data class ContentEntity(
    @PrimaryKey(true)
    val id: Long?,
    val content: String,
    val type: Type
)
