package com.himamis.retex.renderer.android.parser

import org.w3c.dom.Attr
import org.w3c.dom.Node

class AttrA(var impl: Attr) : NodeA(impl as Node),
    com.himamis.retex.renderer.share.platform.parser.Attr {
    override fun getName(): String {
        return impl.name
    }

    override fun isSpecified(): Boolean {
        return impl.specified
    }

    override fun getValue(): String {
        return impl.value
    }
}
