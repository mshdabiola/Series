package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class TopicCategory(
    val id: Long=-1,
    val name: String,
    val subjectId: Long,
)
