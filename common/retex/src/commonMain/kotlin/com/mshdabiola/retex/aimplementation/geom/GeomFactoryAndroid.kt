package com.mshdabiola.retex.aimplementation.geom

import com.himamis.retex.renderer.share.platform.geom.Area
import com.himamis.retex.renderer.share.platform.geom.GeomFactory
import com.himamis.retex.renderer.share.platform.geom.Line2D
import com.himamis.retex.renderer.share.platform.geom.Point2D
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape

class GeomFactoryAndroid : GeomFactory() {
    override fun createLine2D(x1: Double, y1: Double, x2: Double, y2: Double): Line2D {
        return Line2DA(x1, y1, x2, y2)
    }

    override fun createRectangle2D(x: Double, y: Double, w: Double, h: Double): Rectangle2D {
        return Rectangle2DA(x, y, w, h)
    }

    override fun createRoundRectangle2D(
        x: Double, y: Double,
        w: Double, h: Double, arcw: Double, arch: Double
    ): RoundRectangle2D {
        return RoundRectangle2DA(Rectangle2DA(x, y, w, h), arcw, arch)
    }

    override fun createPoint2D(x: Double, y: Double): Point2D {
        return Point2DA(x, y)
    }

    override fun createArea(rect: Shape): Area? {
        return null
    }

    override fun newArea(): Area? {
        return null
    }
}