/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class LearningObjective(
    val learningObjectiveId: Long = -1, // PK, Auto-generate
    val objectiveText: List<Content>,
    val lessonTopicId: Long, // FK to LessonTopic.topicId
)
