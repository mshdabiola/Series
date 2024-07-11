package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class Examination(
    val id: Long=-1,
    val subjectId: Long,
    val year: Long,
    val duration: Long,
)
