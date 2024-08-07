package com.mshdabiola.seriesmodel.serial

import com.mshdabiola.seriesmodel.Content
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.json.Json

fun String.toContent(): List<Content> {
    return Json.decodeFromString(this)
}

fun List<Content>.asString(): String {
    return Json.encodeToString(ListSerializer(Content.serializer()), this)
}

fun Content.toSer() = Content(content, type)
fun Content.asModel() = Content(content, type)
