package com.mshdabiola.datastore.model

import kotlinx.serialization.Serializable

@Serializable

data class InstructionSer(
    val id: Long? = null,
    val examId: Long,
    val title: String,
    val contentSer: List<ContentSer>,
)
