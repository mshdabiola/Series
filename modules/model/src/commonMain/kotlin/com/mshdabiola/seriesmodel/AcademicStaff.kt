/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

data class AcademicStaff(
    val staffId: Int = 0, // PK, Auto-generate
    val staffType: StaffType,
    val name: String,
    val contactDetails: String,

    val password: String,
    val imagePath: String,
)
