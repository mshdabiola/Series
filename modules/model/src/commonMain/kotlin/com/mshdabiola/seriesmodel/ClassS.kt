/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class ClassS(
    val classId: Long = -1, // PK, Auto-generate
    val className: String, // e.g., "10A", "S5B"
    val gradeLevelId: Long, // FK to GradeLevel.gradeLevelId
    val teacherStaffId: Long?, // FK to AcademicStaff.staffId (Class Teacher), nullable
)
