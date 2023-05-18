package com.mshdabiola.retex.aimplementation.graphics

import androidx.compose.ui.geometry.Rect


class ScaleStack {
    private val mScaleStackX: MutableList<Float>
    private val mScaleStackY: MutableList<Float>

    init {
        mScaleStackX = ArrayList()
        mScaleStackX.add(1.0f)
        mScaleStackY = ArrayList()
        mScaleStackY.add(1.0f)
    }

    fun appendScale(sx: Double, sy: Double) {
        val x = scaleX(sx.toFloat())
        val y = scaleY(sy.toFloat())
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

    fun scaleRectF(rect: Rect): Rect {

        val b = rect.bottom * scaleY
        val t = rect.top * scaleY
        val l = rect.left * scaleX
        val r = rect.right * scaleX
        return Rect(l, t, r, b)
    }

}