package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.Node
import org.w3c.dom.NamedNodeMap

class NamedNodeMapA(private val impl: NamedNodeMap) :
    com.himamis.retex.renderer.share.platform.parser.NamedNodeMap {
    override fun getLength(): Int {
        return impl.length
    }

    override fun item(index: Int): Node {
        return NodeA(impl.item(index))
    }
}
