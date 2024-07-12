/*
 *abiola 2024
 */

package com.mshdabiola.database.model.exam

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mshdabiola.database.model.SeriesEntity

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = SeriesEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("seriesId"),
            onDelete = ForeignKey.SET_DEFAULT,
        ),
    ],
    indices = [Index(value = ["seriesId"])],

    tableName = "subject_table")
data class SubjectEntity(
    @PrimaryKey(true)
    val id: Long?,
    @ColumnInfo(defaultValue = "1")
    val seriesId:Long,
    val title: String,
)
