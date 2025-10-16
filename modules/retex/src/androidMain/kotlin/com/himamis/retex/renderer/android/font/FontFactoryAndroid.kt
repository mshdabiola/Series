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
        string: String,
        font: Font,
        fontRenderContext: FontRenderContext,
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
