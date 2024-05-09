/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.mshdabiola.model.data.Content

@Entity(tableName = "instruction_table")
data class InstructionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val examId: Long,
    val title: String,
    val content: List<Content>
)
