package com.mshdabiola.retex.aimplementation.graphics

import androidx.compose.ui.graphics.Matrix
import com.himamis.retex.renderer.share.platform.graphics.Transform

class TransformA() : Transform {

    var matrix = Matrix(FloatArray(9))


    constructor(matrix: Matrix) : this() {
        this.matrix = matrix
    }

    val nativeObject: Any
        get() = this

    override fun getTranslateX(): Double {
        TODO("Not yet implemented")
    }

    override fun getTranslateY(): Double {
        TODO("Not yet implemented")
    }

    override fun getScaleX(): Double {
        return 1.0
    }

    override fun getScaleY(): Double {
        return 1.0
    }

    override fun getShearX(): Double {
        TODO("Not yet implemented")
    }

    override fun getShearY(): Double {
        TODO("Not yet implemented")
    }

    override fun createClone(): Transform {
        TODO("Not yet implemented")
    }

    override fun scale(sx: Double, sy: Double) {
        matrix.scale(sx.toFloat(), sy.toFloat())
    }

    override fun translate(tx: Double, ty: Double) {
        matrix.translate(tx.toFloat(), ty.toFloat())
    }

    override fun shear(sx: Double, sy: Double) {
//        setSkew(sx.toFloat(), sy.toFloat())
    }
}