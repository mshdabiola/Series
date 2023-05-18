package com.mshdabiola.retex.aimplementation.geom

import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D

data class RoundRectangle2DA(var rectangle2DA: Rectangle2DA, var arcw: Double, var arch: Double) :
    RoundRectangle2D {


    fun setRectangle(x: Double, y: Double, w: Double, h: Double) {
        rectangle2DA = Rectangle2DA(x, y, w, h)
    }

    override fun getArcW(): Double {
        return arcw
    }

    override fun getArcH(): Double {
        return arch
    }

    override fun getX(): Double {
        return rectangle2DA.x
    }

    override fun getY(): Double {
        return rectangle2DA.y
    }

    override fun getWidth(): Double {
        return rectangle2DA.width
    }

    override fun getHeight(): Double {
        return rectangle2DA.height
    }


    override fun setRoundRectangle(
        x: Double,
        y: Double,
        w: Double,
        h: Double,
        arcw: Double,
        arch: Double
    ) {
        rectangle2DA = Rectangle2DA(x, y, w, h)
        this.arcw = arcw
        this.arch = arch
    }
}