package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class GradeLevel(
    val gradeLevelId: Long = -1, // PK, Auto-generate
    val gradeName: String, // e.g., "Grade 10", "Senior 5"
    val levelNumber: Long, // e.g., 10, 5
    val schoolId: Long,
)
