/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import java.time.LocalDateTime

data class StudentAnswerSheet(
    val answerSheetId: Int = 0, // PK, Auto-generate
    val studentId: Int, // FK to Student.studentId
    val examScheduleId: Int, // FK to ExamSchedule.examScheduleId
    val submissionDate: LocalDateTime? = null, // Could be nullable
)
