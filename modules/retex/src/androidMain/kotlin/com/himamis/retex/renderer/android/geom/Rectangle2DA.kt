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

import android.graphics.Rect
import android.graphics.RectF
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D

class Rectangle2DA : Rectangle2D {
    var rectF: RectF
        private set

    constructor(rect: Rect?) {
        rectF = RectF()
        rectF.set(rect!!)
    }

    constructor(x: Double, y: Double, w: Double, h: Double) {
        rectF = RectF()
        setRectangle(x, y, w, h)
    }

    override fun setRectangle(x: Double, y: Double, w: Double, h: Double) {
        val left = x.toFloat()
        val top = y.toFloat()
        val right = left + w.toFloat()
        val bottom = top + h.toFloat()
        rectF[left, top, right] = bottom
    }

    override fun getBounds2DX(): Rectangle2D {
        return this
    }

    override fun getX(): Double {
        return rectF.left.toDouble()
    }

    override fun getY(): Double {
        return rectF.top.toDouble()
    }

    override fun getWidth(): Double {
        return rectF.width().toDouble()
    }

    override fun getHeight(): Double {
        return rectF.height().toDouble()
    }
}
