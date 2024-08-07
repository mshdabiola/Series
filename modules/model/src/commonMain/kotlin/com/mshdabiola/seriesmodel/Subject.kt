package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val id: Long = -1,
    val seriesId: Long,
    val title: String,
)
