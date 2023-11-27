package com.himamis.retex.renderer.android.graphics

import android.graphics.Paint
import com.himamis.retex.renderer.share.platform.graphics.BasicStroke

class BasicStrokeA @JvmOverloads constructor(
    val width: Double,
    val miterLimit: Double,
    val cap: Int,
    val join: Int,
    val dashes: DoubleArray? = null,
) : BasicStroke {

    constructor(width: Double, miterLimit: Double, cap: Paint.Cap, join: Paint.Join) : this(
        width,
        miterLimit,
        getCap(cap),
        getJoin(join),
        null
    )

    constructor(width: Double, dashes: DoubleArray?) : this(
        width,
        10.0,
        BasicStroke.CAP_BUTT,
        BasicStroke.JOIN_MITER,
        dashes
    )

    val nativeObject: Any?
        get() = null
    val nativeCap: Paint.Cap
        get() = when (cap) {
            BasicStroke.CAP_BUTT -> Paint.Cap.BUTT
            BasicStroke.CAP_ROUND -> Paint.Cap.ROUND
            BasicStroke.CAP_SQUARE -> Paint.Cap.SQUARE
            else -> Paint.Cap.BUTT
        }
    val nativeJoin: Paint.Join
        get() = when (join) {
            BasicStroke.JOIN_BEVEL -> Paint.Join.BEVEL
            BasicStroke.JOIN_MITER -> Paint.Join.MITER
            BasicStroke.JOIN_ROUND -> Paint.Join.ROUND
            else -> Paint.Join.BEVEL
        }

    companion object {
        private fun getCap(cap: Paint.Cap): Int {
            return when (cap) {
                Paint.Cap.BUTT -> BasicStroke.CAP_BUTT
                Paint.Cap.ROUND -> BasicStroke.CAP_ROUND
                Paint.Cap.SQUARE -> BasicStroke.CAP_SQUARE
                else -> BasicStroke.CAP_BUTT
            }
        }

        private fun getJoin(join: Paint.Join): Int {
            return when (join) {
                Paint.Join.BEVEL -> BasicStroke.JOIN_BEVEL
                Paint.Join.MITER -> BasicStroke.JOIN_MITER
                Paint.Join.ROUND -> BasicStroke.JOIN_ROUND
                else -> BasicStroke.JOIN_BEVEL
            }
        }
    }
}
