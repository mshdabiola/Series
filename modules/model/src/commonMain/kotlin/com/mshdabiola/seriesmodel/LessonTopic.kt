/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class LessonTopic(
    val topicId: Long = -1, // PK, Auto-generate
    val topicName: String, // e.g., "Algebra", "Photosynthesis"
    val courseId: Long, // FK to Course.courseId
)
