/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class StudentAnswer(
    val studentAnswerId: Int = 0, // PK, Auto-generate
    val answerSheetId: Int, // FK to StudentAnswerSheet.answerSheetId
    val examQuestionId: Int, // FK to ExamQuestion.questionId
    val answerText: String? = null, // For written answers, nullable
    val choiceOptionId: Int? = null, // FK to ChoiceOption.optionId for MCQ, nullable
    val isCorrect: Boolean? = null, // Calculated, nullable initially
    val marksObtained: Int? = null, // Evaluated, nullable initially
)
