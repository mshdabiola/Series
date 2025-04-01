/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
data class Student(
    val studentId: Long = -1, // PK, Auto-generate
    val name: String,
    val dateOfBirth: LocalDate,
    val admissionNumber: String,
    val classId: Long, // FK to Class.classId
)
