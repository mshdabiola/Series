package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Series(
    val id: Long = -1,
    val userId: Long,
    val name: String,
)
