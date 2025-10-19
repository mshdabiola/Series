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
package com.himamis.retex.renderer.desktop

import com.himamis.retex.renderer.desktop.graphics.ColorD
import com.himamis.retex.renderer.desktop.graphics.Graphics2DD
import com.himamis.retex.renderer.share.Colors
import com.himamis.retex.renderer.share.TeXIcon
import java.awt.Component
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.Icon

object IconHelper {
    fun createIcon(icon: TeXIcon): Icon {
        return object : Icon {
            override fun paintIcon(c: Component, g: Graphics, x: Int, y: Int) {
                val g2d = Graphics2DD(g as Graphics2D)
                icon.paintIcon({
                    if (c != null) {
                        ColorD(c.foreground)
                    } else {
                        Colors.BLACK
                    }
                }, g2d, x.toDouble(), y.toDouble())
                icon.paintCursor(g2d, y.toDouble())
            }

            override fun getIconWidth(): Int {
                return icon.iconWidth
            }

            override fun getIconHeight(): Int {
                return icon.iconHeight
            }
        }
    }
}
