package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class TopicWithCategory(
    val id: Long = -1,
    val topicCategory: TopicCategory,
    val title: String,
)
