package com.himamis.retex.renderer.android.font

import android.graphics.Typeface
import com.himamis.retex.renderer.share.CharFont
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.font.TextAttribute
import com.himamis.retex.renderer.share.platform.geom.Shape

class FontA : Font {
    var typeface: Typeface
        private set
    var size: Int
        private set
    private var mName: String

    constructor(name: String, typeface: Typeface, size: Int) {
        mName = name
        this.typeface = typeface
        this.size = size
    }

    constructor(name: String, style: Int, size: Int) {
        typeface = Typeface.create(name, getTypefaceStyle(style))
        mName = name
        this.size = size
    }

    override fun deriveFont(map: Map<TextAttribute, Any>): Font {
        // FIXME cannot infer Font from map
        return this
    }

    override fun deriveFont(type: Int): Font {
        val typefaceStyle = getTypefaceStyle(type)
        return FontA(
            mName,
            Typeface.create(
                typeface,
                typefaceStyle,
            ),
            size,
        )
    }

    override fun isEqual(f: Font): Boolean {
        val font = f as FontA
        return mName == font.mName && typeface == font.typeface && size == font.size
    }

    override fun getScale(): Int {
        return 1
    }

    // @Override omit - this method will be removed from Font soon
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

    companion object {
        private fun getTypefaceStyle(fontStyle: Int): Int {
            var typefaceStyle = Typeface.NORMAL
            if (fontStyle == Font.BOLD) {
                typefaceStyle = Typeface.BOLD
            } else if (fontStyle == Font.ITALIC) {
                typefaceStyle = Typeface.ITALIC
            } else if (fontStyle == Font.BOLD or Font.ITALIC) {
                typefaceStyle = Typeface.BOLD_ITALIC
            }
            return typefaceStyle
        }
    }
}
