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

import android.graphics.Paint
import android.graphics.Rect
import com.himamis.retex.renderer.android.geom.Rectangle2DA
import com.himamis.retex.renderer.android.graphics.Graphics2DA
import com.himamis.retex.renderer.share.platform.font.TextLayout
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface

class TextLayoutA(
    private val mString: String,
    private val mFont: FontA,
    fontRenderContext: FontRenderContextA,
) : TextLayout {
    private val mPaint: Paint?

    init {
        mPaint = fontRenderContext.paint
    }

    override fun getBounds(): Rectangle2D {
        updatePaint()
        val bounds = Rect()
        mPaint!!.getTextBounds(mString, 0, mString.length, bounds)
        return Rectangle2DA(bounds)
    }

    override fun draw(graphics: Graphics2DInterface, x: Int, y: Int) {
        if (graphics is Graphics2DA) {
            updatePaint()
            graphics.drawString(mString, x, y, mPaint)
        }
    }

    private fun updatePaint() {
        mPaint!!.setTypeface(mFont.typeface)
        mPaint.textSize = mFont.size.toFloat()
        mPaint.style = Paint.Style.FILL
    }
}
