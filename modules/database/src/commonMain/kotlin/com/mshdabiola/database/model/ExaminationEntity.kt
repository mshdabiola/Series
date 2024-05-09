/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "examination_table")
data class ExaminationEntity(
    @PrimaryKey(true)
    val id: Long?,
    val subjectId: Long,
    val year: Long,
    val isObjectiveOnly: Boolean,
    val duration: Long,
    val updateTime: Long
)
