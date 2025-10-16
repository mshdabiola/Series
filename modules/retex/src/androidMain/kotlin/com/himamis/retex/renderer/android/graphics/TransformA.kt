/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.himamis.retex.renderer.android.graphics

import android.graphics.Matrix
import com.himamis.retex.renderer.share.platform.graphics.Transform

class TransformA() : Matrix(), Transform {
    private val mValues: FloatArray

    init {
        mValues = FloatArray(9)
    }

    constructor(matrix: Matrix?) : this() {
        set(matrix)
    }

    val nativeObject: Any
        get() = this

    override fun getTranslateX(): Double {
        getValues(mValues)
        return mValues[MTRANS_X].toDouble()
    }

    override fun getTranslateY(): Double {
        getValues(mValues)
        return mValues[MTRANS_Y].toDouble()
    }

    override fun getScaleX(): Double {
        getValues(mValues)
        return mValues[MSCALE_X].toDouble()
    }

    override fun getScaleY(): Double {
        getValues(mValues)
        return mValues[MSCALE_Y].toDouble()
    }

    override fun getShearX(): Double {
        getValues(mValues)
        return mValues[MSKEW_X].toDouble()
    }

    override fun getShearY(): Double {
        getValues(mValues)
        return mValues[MSKEW_Y].toDouble()
    }

    override fun createClone(): Transform {
        return TransformA(this)
    }

    override fun scale(sx: Double, sy: Double) {
        setScale(sx.toFloat(), sy.toFloat())
    }

    override fun translate(tx: Double, ty: Double) {
        setTranslate(tx.toFloat(), ty.toFloat())
    }

    override fun shear(sx: Double, sy: Double) {
        setSkew(sx.toFloat(), sy.toFloat())
    }
}
