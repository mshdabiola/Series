/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ExamAttendance(
    val examAttendanceId: Long = -1, // PK, Auto-generate, not part of composite key
    val studentId: Long, // FK to Student.studentId
    val examScheduleId: Long, // FK to ExamSchedule.examScheduleId
    val attendanceStatus: AttendanceStatus,
    val reason: String? = null, // Optional reason for absence/late
)
