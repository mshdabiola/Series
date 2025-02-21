/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class LessonTopic(
    val topicId: Int = 0, // PK, Auto-generate
    val topicName: String, // e.g., "Algebra", "Photosynthesis"
    val courseId: Int, // FK to Course.courseId
)
