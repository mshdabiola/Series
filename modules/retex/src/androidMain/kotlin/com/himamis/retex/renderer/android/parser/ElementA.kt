package com.himamis.retex.renderer.android.parser

import com.himamis.retex.renderer.share.platform.parser.NamedNodeMap
import com.himamis.retex.renderer.share.platform.parser.NodeList
import org.w3c.dom.Element
import org.w3c.dom.Node

class ElementA(impl: Element) :
    NodeA(impl as Node),
    com.himamis.retex.renderer.share.platform.parser.Element {
    private val impl: Element?

    init {
        this.impl = impl
    }

    override fun getElementsByTagName(name: String): NodeList {
        return NodeListA(impl!!.getElementsByTagName(name))
    }

    override fun getAttribute(name: String): String {
        return impl!!.getAttribute(name)
    }

    override fun getTagName(): String {
        return impl!!.tagName
    }

    override fun getChildNodes(): NodeList {
        return NodeListA(impl!!.childNodes)
    }

    override fun getAttributes(): NamedNodeMap {
        return NamedNodeMapA(impl!!.attributes)
    }

    override fun isNull(): Boolean {
        return impl == null
    }
}
