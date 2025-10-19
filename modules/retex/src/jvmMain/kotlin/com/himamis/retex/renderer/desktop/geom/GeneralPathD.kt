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

import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape
import java.awt.geom.GeneralPath
import java.awt.geom.Path2D

class GeneralPathD : ShapeD {
    private val impl: GeneralPath

    constructor(g: GeneralPath) {
        impl = g
    }

    constructor() {
        // default winding rule changed for ggb50 (for Polygons) #3983
        impl = GeneralPath(Path2D.WIND_EVEN_ODD)
    }

    constructor(rule: Int) {
        impl = GeneralPath(rule)
    }

    override fun getBounds2DX(): Rectangle2D {
        return Rectangle2DD(impl.bounds2D)
    }

    companion object {
        fun getAwtGeneralPath(gp: Shape?): GeneralPath? {
            if (gp !is GeneralPathD) {
                if (gp != null) {
                    FactoryProvider.debugS("other type")
                }
                return null
            }
            return gp.impl
        }
    }
}
