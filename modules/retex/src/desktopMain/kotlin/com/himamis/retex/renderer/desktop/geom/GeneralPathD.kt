package com.himamis.retex.renderer.desktop.geom

import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape
import java.awt.geom.GeneralPath
import java.awt.geom.Path2D

class GeneralPathD : ShapeD {
    private val impl: GeneralPath

    constructor(g: GeneralPath) {
        impl = g
    }

    constructor() {
        // default winding rule changed for ggb50 (for Polygons) #3983
        impl = GeneralPath(Path2D.WIND_EVEN_ODD)
    }

    constructor(rule: Int) {
        impl = GeneralPath(rule)
    }

    override fun getBounds2DX(): Rectangle2D {
        return Rectangle2DD(impl.bounds2D)
    }

    companion object {
        fun getAwtGeneralPath(gp: Shape?): GeneralPath? {
            if (gp !is GeneralPathD) {
                if (gp != null) {
                    FactoryProvider.debugS("other type")
                }
                return null
            }
            return gp.impl
        }
    }
}
