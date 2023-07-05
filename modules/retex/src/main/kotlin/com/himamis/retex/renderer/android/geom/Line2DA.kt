package com.himamis.retex.renderer.android.geom

import android.graphics.PointF
import com.himamis.retex.renderer.share.platform.geom.Line2D

class Line2DA(x1: Double, y1: Double, x2: Double, y2: Double) : Line2D {
    val startPoint: PointF
    val endPoint: PointF

    init {
        startPoint = PointF()
        endPoint = PointF()
        setLine(x1, y1, x2, y2)
    }

    override fun setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        startPoint[x1.toFloat()] = y1.toFloat()
        endPoint[x2.toFloat()] = y2.toFloat()
    }

    override fun getX1(): Double {
        return startPoint.x.toDouble()
    }

    override fun getY1(): Double {
        return startPoint.y.toDouble()
    }

    override fun getX2(): Double {
        return endPoint.x.toDouble()
    }

    override fun getY2(): Double {
        return endPoint.y.toDouble()
    }
}
