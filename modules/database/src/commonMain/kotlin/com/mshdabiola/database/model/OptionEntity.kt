/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import com.mshdabiola.database.converter.ContentSer
import com.mshdabiola.model.data.Content

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = QuestionEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("questionId"),
            onDelete = ForeignKey.CASCADE
        )
    ],

    tableName = "option_table"
)
data class OptionEntity(
    @PrimaryKey(true)
    val id: Long?,
    val number: Long,
    @ColumnInfo(index = true)
    val questionId: Long,
    val examId: Long,
    val title: String,
    val contents: List<ContentSer>,
    val isAnswer: Boolean
)
