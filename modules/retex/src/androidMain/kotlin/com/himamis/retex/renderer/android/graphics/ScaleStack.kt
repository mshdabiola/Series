package com.himamis.retex.renderer.android.graphics

import android.graphics.Bitmap
import android.graphics.RectF

class ScaleStack {
    private val mScaleStackX: MutableList<Float>
    private val mScaleStackY: MutableList<Float>

    init {
        mScaleStackX = ArrayList()
        mScaleStackX.add(1.0f)
        mScaleStackY = ArrayList()
        mScaleStackY.add(1.0f)
    }

    fun appendScale(sx: Float, sy: Float) {
        val x = scaleX(sx)
        val y = scaleY(sy)
        mScaleStackX[scaleStackXTopIndex] = x
        mScaleStackY[scaleStackYTopIndex] = y
    }

    val scaleX: Float
        get() = mScaleStackX[scaleStackXTopIndex]
    val scaleY: Float
        get() = mScaleStackY[scaleStackYTopIndex]

    fun pushScaleValues() {
        mScaleStackX.add(scaleX)
        mScaleStackY.add(scaleY)
    }

    fun popScaleValues() {
        mScaleStackX.removeAt(scaleStackXTopIndex)
        mScaleStackY.removeAt(scaleStackYTopIndex)
    }

    private val scaleStackXTopIndex: Int
        private get() = mScaleStackX.size - 1
    private val scaleStackYTopIndex: Int
        private get() = mScaleStackY.size - 1

    fun scaleX(x: Float): Float {
        return x * scaleX
    }

    fun scaleY(y: Float): Float {
        return y * scaleY
    }

    fun scaleFontSize(size: Float): Float {
        return scaleByLargerScale(size)
    }

    fun scaleThickness(thickness: Float): Float {
        return scaleByLargerScale(thickness)
    }

    private fun scaleByLargerScale(value: Float): Float {
        return Math.max(scaleX, scaleY) * value
    }

    fun scaleRectF(rect: RectF): RectF {
        rect.bottom *= scaleY
        rect.top *= scaleY
        rect.left *= scaleX
        rect.right *= scaleX
        return rect
    }

    fun scaleBitmap(bitmap: Bitmap?): Bitmap {
        val scaledWidth = Math.round(scaleX(bitmap!!.width.toFloat()))
        val scaledHeight = Math.round(scaleY(bitmap.height.toFloat()))
        return Bitmap.createScaledBitmap(
            bitmap, scaledWidth, scaledHeight,
            false
        )
    }
}
