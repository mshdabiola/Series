package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Topic(
    val id: Long = -1,
    val categoryId: Long,
    val title: String,
)
