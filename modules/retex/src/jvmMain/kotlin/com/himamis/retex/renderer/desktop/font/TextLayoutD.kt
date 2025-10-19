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

import com.himamis.retex.renderer.desktop.geom.Rectangle2DD
import com.himamis.retex.renderer.desktop.graphics.Graphics2DD
import com.himamis.retex.renderer.share.platform.font.TextLayout
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import java.awt.Font
import java.awt.font.FontRenderContext

class TextLayoutD(
    string: String?,
    font: Font?,
    fontRenderContext: FontRenderContext?,
) : TextLayout {
    private val layout: java.awt.font.TextLayout

    init {
        layout = java.awt.font.TextLayout(string, font, fontRenderContext)
    }

    override fun getBounds(): Rectangle2D {
        return Rectangle2DD(layout.bounds)
    }

    override fun draw(graphics: Graphics2DInterface, x: Int, y: Int) {
        // can be GraphicsStub
        if (graphics is Graphics2DD) {
            layout.draw(graphics.impl, x.toFloat(), y.toFloat())
        }
    }
}
