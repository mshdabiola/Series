/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.mshdabiola.seriesdatabase.util.Converters

@Entity(
    tableName = "lesson_topics",
    foreignKeys = [
        ForeignKey(
            entity = CourseEntity::class,
            parentColumns = ["course_id"],
            childColumns = ["course_id_fk"],
            onDelete = ForeignKey.CASCADE, // Delete topics if course is deleted
        ),
    ],
    indices = [
        Index(value = ["course_id_fk"]),
    ],
)
@TypeConverters(Converters::class)
data class LessonTopicEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "topic_id")
    val topicId: Long?, // PK, Auto-generate

    @ColumnInfo(name = "topic_name")
    val topicName: String, // e.g., "Algebra", "Photosynthesis"

    @ColumnInfo(name = "course_id_fk") // Foreign Key column name
    val courseId: Long, // FK to Course.courseId
)
