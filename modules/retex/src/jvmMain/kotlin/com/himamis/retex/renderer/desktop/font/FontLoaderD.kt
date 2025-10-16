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

import com.himamis.retex.renderer.desktop.FactoryProviderDesktop
import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontLoader
import java.awt.GraphicsEnvironment
import java.io.IOException
class FontLoaderD : FontLoader {
    @Throws(ResourceParseException::class)
    override fun loadFont(name: String): Font {
        FactoryProvider.debugS("loadFont():$name")
        val fontIn = FactoryProviderDesktop::class.java.getResourceAsStream(
            "/com/himamis/retex/renderer/desktop/$name",
        )
        return try {
            val f = java.awt.Font
                .createFont(java.awt.Font.TRUETYPE_FONT, fontIn)
                .deriveFont(FontLoader.PIXELS_PER_POINT.toFloat() * FontLoader.FONT_SCALE_FACTOR)
            val graphicEnv = GraphicsEnvironment
                .getLocalGraphicsEnvironment()
            /**
             * The following fails under java 1.5 graphicEnv.registerFont(f);
             * dynamic load then
             */
            if (SHOULD_REGISTER_FONTS) {
                graphicEnv.registerFont(f)
            }
            FontD(f)
        } catch (e: Exception) {
            throw ResourceParseException(
                "FontLoader" + ": FontLoader '" +
                    name + "'. Error message: " + e.message,
            )
        } finally {
            try {
                fontIn?.close()
            } catch (ioex: IOException) {
                throw RuntimeException("Close threw exception", ioex)
            }
        }
    }

    companion object {
        private const val SHOULD_REGISTER_FONTS = true
    }
}
