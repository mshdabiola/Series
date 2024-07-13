/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = ExaminationEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("examId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["examId"])],

    tableName = "instruction_table")
data class InstructionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val examId: Long,
    val title: String,
    val content: String,
)
