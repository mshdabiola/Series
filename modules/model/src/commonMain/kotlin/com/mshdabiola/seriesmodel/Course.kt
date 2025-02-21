/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class Course(
    val courseId: Int = 0, // PK, Auto-generate
    val courseName: String, // e.g., "Mathematics", "Science"
    val courseCode: String, // e.g., "MATH101", "SCI-G5"
    val gradeLevelId: Int, // FK to GradeLevel.gradeLevelId
)
