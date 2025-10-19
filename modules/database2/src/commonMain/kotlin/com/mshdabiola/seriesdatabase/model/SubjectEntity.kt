/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("seriesId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["seriesId"])],

    tableName = "subject_table",
)
data class SubjectEntity(
    @PrimaryKey(true)
    val id: Long?,
    @ColumnInfo(defaultValue = "1")
    val seriesId: Long,
    val title: String,
)
