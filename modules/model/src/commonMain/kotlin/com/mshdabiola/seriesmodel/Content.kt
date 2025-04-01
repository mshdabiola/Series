package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Content(val content: String, val contentType: ContentType = ContentType.TEXT)
