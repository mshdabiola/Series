/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class StudentAnswer(
    val studentAnswerId: Long = 0, // PK, Auto-generate
    val answerSheetId: Long, // FK to StudentAnswerSheet.answerSheetId
    val examQuestionId: Long, // FK to ExamQuestion.questionId
    val answerText: List<Content>? = null, // For written answers, nullable
    val choiceOptionId: Long? = null, // FK to ChoiceOption.optionId for MCQ, nullable
    val isCorrect: Boolean? = null, // Calculated, nullable initially
    val marksObtained: Long? = null, // Evaluated, nullable initially
)
