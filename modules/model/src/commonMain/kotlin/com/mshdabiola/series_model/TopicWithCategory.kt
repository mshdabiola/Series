package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class TopicWithCategory(
    val id: Long = -1,
    val topicCategory: TopicCategory,
    val title: String,
)
