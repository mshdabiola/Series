/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class School(
    val schoolId: Long = -1, // PK, Room will auto-generate
    val schoolName: String,
    val schoolAddress: String,
    val academicYear: String, // e.g., "2023-2024"
)
