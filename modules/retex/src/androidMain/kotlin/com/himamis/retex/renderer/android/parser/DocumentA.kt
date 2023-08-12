package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.Element
import org.w3c.dom.Document

class DocumentA(private val impl: Document) :
    com.himamis.retex.renderer.share.platform.parser.Document {
    override fun getDocumentElement(): Element {
        return ElementA(impl.documentElement)
    }
}
