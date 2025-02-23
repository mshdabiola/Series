/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class AcademicStaff(
    val staffId: Long = -1, // PK, Auto-generate
    val staffType: StaffType,
    val name: String,
    val contactDetails: String,

    val password: String,
    val imagePath: String,
    val schoolId: Long,
)
