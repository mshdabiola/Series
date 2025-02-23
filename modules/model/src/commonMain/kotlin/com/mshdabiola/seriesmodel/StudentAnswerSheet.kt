/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class StudentAnswerSheet(
    val answerSheetId: Long = -1, // PK, Auto-generate
    val studentId: Long, // FK to Student.studentId
    val examScheduleId: Long, // FK to ExamSchedule.examScheduleId
    val submissionDate: LocalDateTime? = null, // Could be nullable
)
