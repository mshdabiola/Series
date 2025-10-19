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

import com.himamis.retex.renderer.share.platform.geom.Area
import com.himamis.retex.renderer.share.platform.geom.GeomFactory
import com.himamis.retex.renderer.share.platform.geom.Line2D
import com.himamis.retex.renderer.share.platform.geom.Point2D
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape

class GeomFactoryAndroid : GeomFactory() {
    override fun createLine2D(x1: Double, y1: Double, x2: Double, y2: Double): Line2D {
        return Line2DA(x1, y1, x2, y2)
    }

    override fun createRectangle2D(x: Double, y: Double, w: Double, h: Double): Rectangle2D {
        return Rectangle2DA(x, y, w, h)
    }

    override fun createRoundRectangle2D(
        x: Double,
        y: Double,
        w: Double,
        h: Double,
        arcw: Double,
        arch: Double,
    ): RoundRectangle2D {
        return RoundRectangle2DA(x, y, w, h, arcw, arch)
    }

    override fun createPoint2D(x: Double, y: Double): Point2D {
        return Point2DA(x, y)
    }

    override fun createArea(rect: Shape): Area? {
        return null
    }

    override fun newArea(): Area? {
        return null
    }
}
