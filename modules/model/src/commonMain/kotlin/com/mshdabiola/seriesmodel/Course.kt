/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Course(
    val courseId: Long = -1, // PK, Auto-generate
    val courseName: String, // e.g., "Mathematics", "Science"
    val courseCode: String, // e.g., "MATH101", "SCI-G5"
    val gradeLevelId: Long, // FK to GradeLevel.gradeLevelId
)
