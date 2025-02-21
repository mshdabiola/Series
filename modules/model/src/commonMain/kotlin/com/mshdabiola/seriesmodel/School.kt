/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class School(
    val schoolId: Int = 0, // PK, Room will auto-generate
    val schoolName: String,
    val schoolAddress: String,
    val academicYear: String, // e.g., "2023-2024"
)
