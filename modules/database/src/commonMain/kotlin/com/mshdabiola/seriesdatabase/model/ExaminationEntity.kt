/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SubjectEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("subjectId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["subjectId"])],
    tableName = "examination_table",
)
data class ExaminationEntity(
    @PrimaryKey(true)
    val id: Long?,
    val subjectId: Long,
    val year: Long,
    val duration: Long,
)
