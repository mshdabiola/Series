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
        arch: Double,
    ) {
        setRectangle(x, y, w, h)
        mArcw = arcw
        mArch = arch
    }
}
