/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class LearningMaterial(
    val learningMaterialId: Long = -1, // PK, Auto-generate
    val title: String,
    val description: String? = null, // Optional description
    val materialType: MaterialType,
    val filePath: String? = null, // Optional file path
    val url: String? = null, // Optional URL
    val lessonTopicId: Long, // FK to LessonTopic.topicId
)
