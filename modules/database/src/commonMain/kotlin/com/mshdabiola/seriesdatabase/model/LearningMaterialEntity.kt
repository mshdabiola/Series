/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "learning_materials",
    foreignKeys = [
        ForeignKey(
            entity = LessonTopicEntity::class,
            parentColumns = ["topic_id"],
            childColumns = ["lesson_topic_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete material if topic is deleted
        ),
    ],
    indices = [
        Index(value = ["lesson_topic_id_fk"]),
    ],
)
// @TypeConverters(Converters::class)

data class LearningMaterialEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "learning_material_id")
    val learningMaterialId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "title")
    val title: String,

    @ColumnInfo(name = "description")
    val description: String? = null, // Optional description

    @ColumnInfo(name = "material_type")
    val materialType: Int,

    @ColumnInfo(name = "file_path")
    val filePath: String? = null, // Optional file path

    @ColumnInfo(name = "url")
    val url: String? = null, // Optional URL

    @ColumnInfo(name = "lesson_topic_id_fk") // Foreign Key column name
    val lessonTopicId: Long, // FK to LessonTopic.topicId
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
