/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ChoiceOption(
    val optionId: Long = -1, // PK, Auto-generate
    val examQuestionId: Long, // FK to ExamQuestion.questionId
    val optionText: List<Content>,
    val isCorrect: Boolean,
)
