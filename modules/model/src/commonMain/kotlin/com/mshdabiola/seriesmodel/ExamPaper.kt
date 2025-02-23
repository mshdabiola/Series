/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDateTime
import kotlinx.serialization.Serializable

@Serializable
data class ExamPaper(
    val examPaperId: Long = 0, // PK, Auto-generate
    val paperTitle: String? = null, // e.g., "Mid-Term Math Paper", optional title
    val courseId: Long, // FK to Course.courseId
    val creatorStaffId: Long, // FK to AcademicStaff.staffId
    val creationDate: LocalDateTime,
    val examScheduleId: Long? = null, // FK to ExamSchedule.examScheduleId - nullable initially

    val year: Long,
)
