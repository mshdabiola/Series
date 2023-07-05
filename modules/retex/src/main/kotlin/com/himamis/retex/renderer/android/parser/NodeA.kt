package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.Attr
import com.himamis.retex.renderer.share.platform.parser.Element
import org.w3c.dom.Node

open class NodeA(private val impl: Node) : com.himamis.retex.renderer.share.platform.parser.Node {
    override fun getNodeType(): Short {
        return impl.nodeType
    }

    override fun castToElement(): Element {
        return ElementA(impl as org.w3c.dom.Element)
    }

    override fun castToAttr(): Attr {
        return AttrA(impl as org.w3c.dom.Attr)
    }
}
