/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class LearningObjective(
    val learningObjectiveId: Int = 0, // PK, Auto-generate
    val objectiveText: String,
    val lessonTopicId: Int, // FK to LessonTopic.topicId
)
