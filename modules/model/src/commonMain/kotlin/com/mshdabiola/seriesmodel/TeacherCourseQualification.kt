/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class TeacherCourseQualification(
    val teacherStaffId: Long,
    val courseId: Long,
    val qualificationDate: LocalDate?,
    val notes: String?,
)
