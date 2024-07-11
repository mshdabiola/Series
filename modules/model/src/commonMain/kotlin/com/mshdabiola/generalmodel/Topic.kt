package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Long=-1,
    val subjectId: Long,
    val title: String,
)
