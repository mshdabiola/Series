package com.himamis.retex.renderer.android.font

import android.content.res.AssetManager
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontFactory
import com.himamis.retex.renderer.share.platform.font.FontLoader
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.font.TextAttributeProvider
import com.himamis.retex.renderer.share.platform.font.TextLayout

class FontFactoryAndroid(private val mAssetManager: AssetManager) : FontFactory() {
    override fun createFont(name: String, style: Int, size: Int): Font {
        return FontA(name, style, size)
    }

    override fun createTextLayout(
        string: String, font: Font,
        fontRenderContext: FontRenderContext
    ): TextLayout {
        return TextLayoutA(string, font as FontA, fontRenderContext as FontRenderContextA)
    }

    override fun createTextAttributeProvider(): TextAttributeProvider {
        return TextAttributeProviderA()
    }

    override fun createFontLoader(): FontLoader {
        return FontLoaderA(mAssetManager)
    }
}
