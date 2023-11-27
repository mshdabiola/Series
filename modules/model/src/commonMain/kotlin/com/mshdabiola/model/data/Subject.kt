package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val id: Long = -1,
    val name: String,
)
