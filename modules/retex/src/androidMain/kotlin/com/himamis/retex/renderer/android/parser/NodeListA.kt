package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.Node
import org.w3c.dom.NodeList

class NodeListA(private val impl: NodeList) :
    com.himamis.retex.renderer.share.platform.parser.NodeList {
    override fun getLength(): Int {
        return impl.length
    }

    override fun item(index: Int): Node {
        return NodeA(impl.item(index))
    }
}
