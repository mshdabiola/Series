/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class ClassS(
    val classId: Int = 0, // PK, Auto-generate
    val className: String, // e.g., "10A", "S5B"
    val gradeLevelId: Int, // FK to GradeLevel.gradeLevelId
    val teacherStaffId: Int?, // FK to AcademicStaff.staffId (Class Teacher), nullable
)
