/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import java.time.LocalDate

data class Student(
    val studentId: Int = 0, // PK, Auto-generate
    val name: String,
    val dateOfBirth: LocalDate,
    val admissionNumber: String,
    val classId: Int, // FK to Class.classId
)
