package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class Content(val content: String, val type: Type = Type.TEXT)
