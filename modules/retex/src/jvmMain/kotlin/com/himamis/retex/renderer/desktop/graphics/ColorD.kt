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

import java.awt.Color

class ColorD(r: Int, g: Int, b: Int, a: Int) :
    Color(r, g, b, a),
    com.himamis.retex.renderer.share.platform.graphics.Color {
    constructor(color: Color) : this(color.red, color.green, color.blue, color.alpha)

    val nativeObject: Any
        get() = this
    val color: Int
        get() = rgb

    override fun equals(other: Any?): Boolean {
        if (other is ColorD) {
            val c = other
            return c.rgb == rgb && c.alpha == alpha
        }
        return false
    }

    companion object {
        private const val serialVersionUID = 1L
        operator fun get(
            bgColor: Color?,
        ): com.himamis.retex.renderer.share.platform.graphics.Color? {
            return bgColor?.let { ColorD(it) }
        }
    }
}
