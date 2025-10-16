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
package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.parser.Document
import com.himamis.retex.renderer.share.platform.parser.Parser
import java.io.InputStream
import javax.xml.parsers.DocumentBuilderFactory

class ParserA : Parser {
    private val factory: DocumentBuilderFactory

    init {
        factory = DocumentBuilderFactory.newInstance()
    }

    @Throws(ResourceParseException::class)
    override fun parse(input: Any): Document {
        // On the desktop platform, the input is an InputSource object
        // Please refer to the ResourceLoaderD class
        val `is` = input as InputStream
        val document = tryParse(`is`)
        return DocumentA(document)
    }

    @Throws(ResourceParseException::class)
    private fun tryParse(`is`: InputStream): org.w3c.dom.Document {
        return try {
            factory.newDocumentBuilder().parse(`is`)
        } catch (ex: Exception) {
            val rpe = ResourceParseException("Could not parse resource", ex)
            throw rpe
        }
    }

    override fun setIgnoringElementContentWhitespace(whitespace: Boolean) {
        factory.isIgnoringElementContentWhitespace = whitespace
    }

    override fun setIgnoringComments(ignoreComments: Boolean) {
        factory.isIgnoringComments = ignoreComments
    }
}
