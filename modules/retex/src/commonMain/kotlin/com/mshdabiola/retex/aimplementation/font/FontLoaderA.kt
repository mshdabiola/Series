package com.mshdabiola.retex.aimplementation.font

import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontLoader
import kotlin.math.roundToInt

class FontLoaderA() : FontLoader {

    @Throws(ResourceParseException::class)
    override fun loadFont(name: String): Font {


//        val typeface: android.graphics.Typeface =
//            android.graphics.Typeface.createFromAsset(mAssetManager, name)
        return FontA(name, FontLoader.PIXELS_PER_POINT.roundToInt())
    }
}