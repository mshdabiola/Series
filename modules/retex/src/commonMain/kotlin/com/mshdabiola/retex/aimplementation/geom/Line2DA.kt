package com.mshdabiola.retex.aimplementation.geom

import com.himamis.retex.renderer.share.platform.geom.Line2D


data class Line2DA(var xx1: Double, var yy1: Double, var xx2: Double, var yy2: Double) : Line2D {
    override fun getX1(): Double {
        return xx1
    }

    override fun getY1(): Double {
        return yy1
    }

    override fun getX2(): Double {
        return xx2
    }

    override fun getY2(): Double {
        return yy2
    }

    override fun setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        this.xx2 = x2
        this.xx1 = x1
        this.yy2 = y2
        this.yy1 = y1
    }
}