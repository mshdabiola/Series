package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class Item(val content: String, val type: Type = Type.TEXT)
