/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class ChoiceOption(
    val optionId: Int = 0, // PK, Auto-generate
    val examQuestionId: Int, // FK to ExamQuestion.questionId
    val optionText: String,
    val isCorrect: Boolean,
    val title: String,
    val contents: String,
)
