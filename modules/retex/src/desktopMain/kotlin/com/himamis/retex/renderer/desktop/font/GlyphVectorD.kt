package com.himamis.retex.renderer.desktop.font

import com.himamis.retex.renderer.desktop.geom.GeneralPathD
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.geom.Shape
import java.awt.font.GlyphVector
import java.awt.geom.GeneralPath

class GlyphVectorD(private val impl: GlyphVector) :
    com.himamis.retex.renderer.share.platform.font.GlyphVector() {
    override fun getGlyphOutline(i: Int): Shape? {
        val ret = impl.getGlyphOutline(i)
        if (ret is GeneralPath) {
            return GeneralPathD(ret)
        }
        FactoryProvider.getInstance()
            .debug("unhandled Shape " + ret.javaClass)
        return null
    }
}
