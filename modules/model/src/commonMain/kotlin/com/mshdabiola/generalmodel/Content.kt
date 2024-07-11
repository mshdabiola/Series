package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class Content(val content: String, val type: Type = Type.TEXT)
