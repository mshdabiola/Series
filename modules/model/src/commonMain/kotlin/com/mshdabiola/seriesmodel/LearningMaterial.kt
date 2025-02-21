/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class LearningMaterial(
    val learningMaterialId: Int = 0, // PK, Auto-generate
    val title: String,
    val description: String? = null, // Optional description
    val materialType: MaterialType,
    val filePath: String? = null, // Optional file path
    val url: String? = null, // Optional URL
    val lessonTopicId: Int, // FK to LessonTopic.topicId
)
