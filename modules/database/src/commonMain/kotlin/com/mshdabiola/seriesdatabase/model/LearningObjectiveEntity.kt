/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.mshdabiola.seriesmodel.Content
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = "learning_objectives",
    foreignKeys = [
        ForeignKey(
            entity = LessonTopicEntity::class,
            parentColumns = ["topic_id"],
            childColumns = ["lesson_topic_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete objective if topic is deleted
        ),
    ],
    indices = [
        Index(value = ["lesson_topic_id_fk"]),
    ],
)
//@TypeConverters(Converters::class)
data class LearningObjectiveEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "learning_objective_id")
    val learningObjectiveId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "objective_text")
    val objectiveText: String,

    @ColumnInfo(name = "lesson_topic_id_fk") // Foreign Key column name
    val lessonTopicId: Long, // FK to LessonTopic.topicId
    @ColumnInfo(name = "updated_at") val updatedAt: LocalDateTime,
)
