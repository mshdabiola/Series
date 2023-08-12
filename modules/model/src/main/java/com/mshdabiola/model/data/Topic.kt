package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Long = -1,
    val subjectId: Long,
    val name: String
)
