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

import java.awt.geom.Point2D

class Point2DD(x: kotlin.Double, y: kotlin.Double) :
    Point2D.Double(),
    com.himamis.retex.renderer.share.platform.geom.Point2D {
    init {
        setLocation(x, y)
    }

    override fun setX(x: kotlin.Double) {
        this.x = x
    }

    override fun setY(y: kotlin.Double) {
        this.y = y
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
