/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
