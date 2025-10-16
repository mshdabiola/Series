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
package com.himamis.retex.renderer.desktop.graphics

import java.awt.BasicStroke

class BasicStrokeD : BasicStroke, com.himamis.retex.renderer.share.platform.graphics.BasicStroke {
    constructor(basicStroke: BasicStroke) : this(
        basicStroke.lineWidth.toDouble(),
        basicStroke.endCap,
        basicStroke.lineJoin,
        basicStroke.miterLimit.toDouble(),
    )

    constructor(width: Double, cap: Int, join: Int, miterlimit: Double) : super(
        width.toFloat(),
        cap,
        join,
        miterlimit.toFloat(),
    )

    constructor(thickness: Double, dashes: DoubleArray?) : super(
        thickness.toFloat(),
        CAP_BUTT,
        JOIN_MITER,
        10f,
        doubleToFloat(dashes),
        0f,
    )

    companion object {
        /**
         * Copied from AWTFactory
         */
        private fun doubleToFloat(array: DoubleArray?): FloatArray? {
            if (array == null) {
                return null
            }
            val n = array.size
            val ret = FloatArray(n)
            for (i in 0 until n) {
                ret[i] = array[i].toFloat()
            }
            return ret
        }
    }
}
