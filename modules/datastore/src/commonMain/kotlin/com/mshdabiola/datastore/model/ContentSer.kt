package com.mshdabiola.datastore.model

import com.mshdabiola.model.data.Type
import kotlinx.serialization.Serializable

@Serializable
data class ContentSer(val content: String, val type: Type = Type.TEXT)
