package com.mshdabiola.retex.aimplementation.geom

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D

data class Rectangle2DA(var topLeft: Offset, var size: Size) : Rectangle2D {

    constructor(x: Double, y: Double, w: Double, h: Double) : this(

        Offset(
            x.toFloat(),
            y.toFloat()
        ), Size(w.toFloat(), h.toFloat())

    )

    override fun setRectangle(x: Double, y: Double, w: Double, h: Double) {

        topLeft = Offset(
            x.toFloat(),
            y.toFloat()
        )
        size = Size(w.toFloat(), h.toFloat())

    }


    override fun getBounds2DX(): Rectangle2D {
        return this
    }

    override fun getX(): Double {
        return topLeft.x.toDouble()
    }

    override fun getY(): Double {
        return topLeft.y.toDouble()
    }

    override fun getWidth(): Double {
        return size.width.toDouble()
    }

    override fun getHeight(): Double {
        return size.height.toDouble()
    }
}