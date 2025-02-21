/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class ExamAttendance(
    val examAttendanceId: Int = 0, // PK, Auto-generate, not part of composite key
    val studentId: Int, // FK to Student.studentId
    val examScheduleId: Int, // FK to ExamSchedule.examScheduleId
    val attendanceStatus: AttendanceStatus,
    val reason: String? = null, // Optional reason for absence/late
)
