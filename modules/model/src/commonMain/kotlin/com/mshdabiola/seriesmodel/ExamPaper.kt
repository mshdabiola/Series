/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import java.time.LocalDateTime

data class ExamPaper(
    val examPaperId: Int = 0, // PK, Auto-generate
    val paperTitle: String? = null, // e.g., "Mid-Term Math Paper", optional title
    val courseId: Int, // FK to Course.courseId
    val creatorStaffId: Int, // FK to AcademicStaff.staffId
    val creationDate: LocalDateTime,
    val examScheduleId: Int? = null, // FK to ExamSchedule.examScheduleId - nullable initially

    val year: Long,
)
