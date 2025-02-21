package com.mshdabiola.seriesmodel

data class GradeLevel(
    val gradeLevelId: Int = 0, // PK, Auto-generate
    val gradeName: String, // e.g., "Grade 10", "Senior 5"
    val levelNumber: Int, // e.g., 10, 5
)
