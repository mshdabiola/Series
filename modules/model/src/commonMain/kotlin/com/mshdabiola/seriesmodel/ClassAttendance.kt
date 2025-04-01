/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.serialization.Serializable

@Serializable
data class ClassAttendance(
    val classAttendanceId: Long = -1, // PK, Auto-generate, not part of composite key
    val studentId: Long, // FK to Student.studentId
    val classId: Long, // FK to Class.classId
    val attendanceDate: LocalDate,
    val attendanceTime: LocalTime? = null, // Optional time
    val attendanceStatus: AttendanceStatus,
    val reason: String? = null, // Optional reason for absence/late
)
