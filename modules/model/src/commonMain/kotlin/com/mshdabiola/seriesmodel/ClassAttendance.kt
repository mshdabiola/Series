/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import java.time.LocalDate
import java.time.LocalTime

data class ClassAttendance(
    val classAttendanceId: Int = 0, // PK, Auto-generate, not part of composite key
    val studentId: Int, // FK to Student.studentId
    val classId: Int, // FK to Class.classId
    val attendanceDate: LocalDate,
    val attendanceTime: LocalTime? = null, // Optional time
    val attendanceStatus: AttendanceStatus,
    val reason: String? = null, // Optional reason for absence/late
)
