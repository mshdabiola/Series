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
package com.himamis.retex.renderer.desktop.geom

import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import java.awt.Shape
import java.awt.geom.AffineTransform
import java.awt.geom.Area

class AreaD : Area, com.himamis.retex.renderer.share.platform.geom.Area {
    constructor(s: Shape?) : super(s)
    constructor() : super()

    override fun getBounds2DX(): Rectangle2D {
        return Rectangle2DD(super.getBounds2D())
    }

    override fun add(a: Area) {
        super.add(a)
    }

    override fun duplicate(): com.himamis.retex.renderer.share.platform.geom.Area {
        val clone = super.clone() as Area
        val s: Shape = clone
        return AreaD(s)
    }

    override fun add(a: com.himamis.retex.renderer.share.platform.geom.Area) {
        super.add(a as Area)
    }

    override fun scale(x: Double) {
        transform(AffineTransform.getScaleInstance(x, x))
    }

    override fun translate(x: Double, y: Double) {
        transform(AffineTransform.getTranslateInstance(x, y))
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
