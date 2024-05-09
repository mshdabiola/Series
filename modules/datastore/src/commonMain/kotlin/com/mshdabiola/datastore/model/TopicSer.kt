package com.mshdabiola.datastore.model

import kotlinx.serialization.Serializable

@Serializable
data class TopicSer(
    val id: Long? = null,
    val subjectId: Long,
    val title: String,
)
