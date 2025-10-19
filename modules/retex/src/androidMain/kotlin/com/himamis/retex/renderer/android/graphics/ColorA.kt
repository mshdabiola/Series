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

import com.himamis.retex.renderer.share.platform.graphics.Color

class ColorA : Color {
    var color: Int
        private set

    constructor(color: Int) {
        this.color = color
    }

    constructor(red: Int, green: Int, blue: Int, alpha: Int) {
        color = android.graphics.Color.argb(alpha, red, green, blue)
    }

    val nativeObject: Any
        get() = Integer.valueOf(color)

    override fun hashCode(): Int {
        return color
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ColorA
        return if (color != other.color) false else true
    }
}
