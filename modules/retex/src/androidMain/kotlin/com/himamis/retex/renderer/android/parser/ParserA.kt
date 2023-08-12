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
