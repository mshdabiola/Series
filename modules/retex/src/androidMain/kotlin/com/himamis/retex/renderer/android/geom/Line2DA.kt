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

import android.graphics.PointF
import com.himamis.retex.renderer.share.platform.geom.Line2D

class Line2DA(x1: Double, y1: Double, x2: Double, y2: Double) : Line2D {
    val startPoint: PointF
    val endPoint: PointF

    init {
        startPoint = PointF()
        endPoint = PointF()
        setLine(x1, y1, x2, y2)
    }

    override fun setLine(x1: Double, y1: Double, x2: Double, y2: Double) {
        startPoint[x1.toFloat()] = y1.toFloat()
        endPoint[x2.toFloat()] = y2.toFloat()
    }

    override fun getX1(): Double {
        return startPoint.x.toDouble()
    }

    override fun getY1(): Double {
        return startPoint.y.toDouble()
    }

    override fun getX2(): Double {
        return endPoint.x.toDouble()
    }

    override fun getY2(): Double {
        return endPoint.y.toDouble()
    }
}
