package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class Subject(
    val id: Long = -1,
    val seriesId: Long,
    val title: String,
)