package com.himamis.retex.renderer.android.geom

import android.graphics.RectF
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D

class RoundRectangle2DA(x: Double, y: Double, w: Double, h: Double, arcw: Double, arch: Double) :
    RoundRectangle2D {
    val rectF: RectF
    private var mArcw: Double
    private var mArch: Double

    init {
        rectF = RectF()
        setRectangle(x, y, w, h)
        mArcw = arcw
        mArch = arch
    }

    fun setRectangle(x: Double, y: Double, w: Double, h: Double) {
        val left = x.toFloat()
        val top = y.toFloat()
        val right = left + w.toFloat()
        val bottom = top + h.toFloat()
        rectF[left, top, right] = bottom
    }

    override fun getArcW(): Double {
        return mArcw
    }

    override fun getArcH(): Double {
        return mArch
    }

    override fun getX(): Double {
        return rectF.left.toDouble()
    }

    override fun getY(): Double {
        return rectF.top.toDouble()
    }

    override fun getWidth(): Double {
        return (rectF.right - rectF.left).toDouble()
    }

    override fun getHeight(): Double {
        return (rectF.bottom - rectF.top).toDouble()
    }

    override fun setRoundRectangle(
        x: Double,
        y: Double,
        w: Double,
        h: Double,
        arcw: Double,
        arch: Double
    ) {
        setRectangle(x, y, w, h)
        mArcw = arcw
        mArch = arch
    }
}
