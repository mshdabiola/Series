/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class ExamSchedule(
    val examScheduleId: Long = -1, // PK, Auto-generate
    val examName: String, // e.g., "Mid-Term Exam", "Unit Test 1"
    val examDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classId: Long, // FK to Class.classId
    val examPaperId: Long? = null, // FK to ExamPaper.examPaperId - nullable initially
)
