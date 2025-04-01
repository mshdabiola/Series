/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class CourseGrade(
    val studentId: Long, // FK to Student.studentId
    val courseId: Long, // FK to Course.courseId
    val academicYear: String, // Part of Composite PK
    val gradeValue: String, // e.g., "A", "B+", "75%", "Pass"
    val gradingSystem: String? = null, // Optional e.g., "Letter Grade", "Percentage"
)
