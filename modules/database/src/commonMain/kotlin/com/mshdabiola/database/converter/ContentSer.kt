package com.mshdabiola.database.converter

import com.mshdabiola.model.data.Content
import com.mshdabiola.model.data.Type
import kotlinx.serialization.Serializable

@Serializable
class ContentSer(val content: String, val type: Type = Type.TEXT)

fun ContentSer.toModel() = Content(content, type)

fun Content.toSer() = ContentSer(content, type)