/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import java.time.LocalDate
import java.time.LocalTime

data class ExamSchedule(
    val examScheduleId: Int = 0, // PK, Auto-generate
    val examName: String, // e.g., "Mid-Term Exam", "Unit Test 1"
    val examDate: LocalDate,
    val startTime: LocalTime,
    val endTime: LocalTime,
    val classId: Int, // FK to Class.classId
    val examPaperId: Int? = null, // FK to ExamPaper.examPaperId - nullable initially
)
