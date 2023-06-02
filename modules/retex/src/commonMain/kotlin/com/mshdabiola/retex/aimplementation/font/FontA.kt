package com.mshdabiola.retex.aimplementation.font

//import android.graphics.Typeface
import com.himamis.retex.renderer.share.CharFont
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.font.TextAttribute
import com.himamis.retex.renderer.share.platform.geom.Shape

data class FontA(val name: String, val style: Int, val size: Int) : Font {

    constructor(name: String, size: Int) : this(name, 1, size)

    override fun deriveFont(map: Map<TextAttribute, Any>): Font {
        // FIXME cannot infer Font from map
        return this
    }

    override fun deriveFont(type: Int): Font {

        return this.copy(style = type)
    }

    override fun isEqual(f: Font): Boolean {
        return this == (f as FontA)
    }

    override fun getScale(): Int {
        return 1
    }

    fun getGlyphOutline(frc: FontRenderContext?, valueOf: String?): Shape? {
        return null
    }

    // @Override omit - this method will be removed from Font soon
    override fun canDisplay(ch: Char): Boolean {
        return true
    }

    override fun getGlyphOutline(frc: FontRenderContext, cf: CharFont): Shape? {
        return null
    }

    override fun canDisplay(c: Int): Boolean {
        return true
    }

}