package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Exam(
    val id: Long = -1,
    val subjectID: Long,
    val isObjOnly: Boolean,
    val year: Long,
    val examTime: Long,
)
