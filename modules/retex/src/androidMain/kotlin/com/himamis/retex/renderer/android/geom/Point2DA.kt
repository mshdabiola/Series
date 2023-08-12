package com.himamis.retex.renderer.android.geom

import android.graphics.PointF
import com.himamis.retex.renderer.share.platform.geom.Point2D

class Point2DA(x: Double, y: Double) : Point2D {
    private val point: PointF

    init {
        point = PointF(x.toFloat(), y.toFloat())
    }

    override fun getX(): Double {
        return point.x.toDouble()
    }

    override fun getY(): Double {
        return point.y.toDouble()
    }

    override fun setX(x: Double) {
        point.x = x.toFloat()
    }

    override fun setY(y: Double) {
        point.y = y.toFloat()
    }
}
