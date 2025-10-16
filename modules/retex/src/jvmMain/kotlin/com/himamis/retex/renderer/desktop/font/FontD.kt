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
package com.himamis.retex.renderer.desktop.font

import com.himamis.retex.renderer.share.CharFont
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontLoader
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.font.GlyphVector
import com.himamis.retex.renderer.share.platform.font.TextAttribute
import com.himamis.retex.renderer.share.platform.geom.Shape

class FontD : Font {
    var font: java.awt.Font

    constructor(impl: java.awt.Font) {
        font = impl
    }

    constructor(name: String?, style: Int, size: Int) {
        font = java.awt.Font(name, style, size)
    }

    override fun deriveFont(type: Int): Font {
        return FontD(font.deriveFont(type))
    }

    override fun deriveFont(map: Map<TextAttribute, Any>): Font {
        return FontD(font.deriveFont(convertMap(map)))
    }

    override fun isEqual(f: Font): Boolean {
        return font == (f as FontD).font
    }

    override fun getScale(): Int {
        return FontLoader.FONT_SCALE_FACTOR
    }

    fun createGlyphVector(frc: FontRenderContext?, s: String?): GlyphVector {
        return GlyphVectorD(
            font
                .createGlyphVector(frc as java.awt.font.FontRenderContext?, s),
        )
    }

    override fun getGlyphOutline(frc: FontRenderContext, cf: CharFont): Shape {
        return createGlyphVector(frc, cf.c.toString() + "").getGlyphOutline(0)
    }

    val size: Int
        get() = font.size
    val name: String
        get() = font.name

    override fun canDisplay(ch: Char): Boolean {
        return font.canDisplay(ch)
    }

    override fun canDisplay(c: Int): Boolean {
        return font.canDisplay(c)
    }

    companion object {
        private val helper: MutableMap<java.awt.font.TextAttribute?, Any?> = HashMap()
        private fun convertMap(
            map: Map<TextAttribute, Any>,
        ): Map<java.awt.font.TextAttribute?, Any?> {
            helper.clear()
            for (key in map.keys) {
                helper[(key as TextAttributeD).textAttribute] = map[key]
            }
            return helper
        }
    }
}
