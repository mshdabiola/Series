package com.mshdabiola.retex.aimplementation.geom

import com.himamis.retex.renderer.share.platform.geom.Point2D


data class Point2DA(var xx: Double, var yy: Double) : Point2D {
    override fun getX(): Double {
        return xx
    }

    override fun getY(): Double {
        return yy
    }

    override fun setX(x: Double) {
        xx = x
    }

    override fun setY(y: Double) {
        yy = y
    }
}