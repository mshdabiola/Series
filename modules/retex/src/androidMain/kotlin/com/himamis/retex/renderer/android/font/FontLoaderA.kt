package com.himamis.retex.renderer.android.font

import android.content.res.AssetManager
import android.graphics.Typeface
import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontLoader

class FontLoaderA(private val mAssetManager: AssetManager) : FontLoader {
    @Throws(ResourceParseException::class)
    override fun loadFont(name: String): Font {
        // TODO fontType should be a class object instead of inputstream
        val typeface = Typeface.createFromAsset(mAssetManager, name)
        return FontA(name, typeface, Math.round(FontLoader.PIXELS_PER_POINT).toInt())
    }
}
