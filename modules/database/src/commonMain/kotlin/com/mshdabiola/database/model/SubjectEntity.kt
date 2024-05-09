/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "subject_table")
data class SubjectEntity(
    @PrimaryKey(true)
    val id: Long?,
    val title: String,
)
