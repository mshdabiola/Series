package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class Content(val content: String, val type: Type = Type.TEXT)
