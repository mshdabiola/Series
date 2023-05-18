package com.mshdabiola.retex.aimplementation.graphics

import com.himamis.retex.renderer.share.platform.graphics.BasicStroke

class BasicStrokeA constructor(
    val width: Double,
    val miterLimit: Double,
    val cap: Int,
    val join: Int,
    val dashes: DoubleArray? = null
) : BasicStroke {

    constructor(width: Double, miterLimit: Double, cap: Int, join: Int) : this(
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

    companion object {
        private fun getCap(cap: Int): Int {
            return when (cap) {
                1 -> BasicStroke.CAP_BUTT
                2 -> BasicStroke.CAP_ROUND
                3 -> BasicStroke.CAP_SQUARE
                else -> BasicStroke.CAP_BUTT
            }
        }

        private fun getJoin(join: Int): Int {
            return when (join) {
                1 -> BasicStroke.JOIN_BEVEL
                2 -> BasicStroke.JOIN_MITER
                3 -> BasicStroke.JOIN_ROUND
                else -> BasicStroke.JOIN_BEVEL
            }
        }
    }
}