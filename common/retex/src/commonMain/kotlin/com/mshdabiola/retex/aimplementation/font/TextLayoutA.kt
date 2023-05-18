package com.mshdabiola.retex.aimplementation.font

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import com.himamis.retex.renderer.share.platform.font.TextLayout
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.mshdabiola.retex.aimplementation.geom.Rectangle2DA
import com.mshdabiola.retex.aimplementation.graphics.Graphics2DA

class TextLayoutA(
    private val mString: String,
    private val mFont: FontA,
    fontRenderContext: FontRenderContextA
) : TextLayout {
    // private val mPaint: Paint=fontRenderContext.paint


    override fun getBounds(): Rectangle2D {
        updatePaint()
        val bounds = Rect(Offset.Zero, Offset.Zero)

        return Rectangle2DA(Offset.Zero, Size.Zero)
    }

    override fun draw(graphics: Graphics2DInterface, x: Int, y: Int) {
        if (graphics is Graphics2DA) {
            updatePaint()
            graphics.drawString(mString, x, y)
        }
    }

    private fun updatePaint() {
//        mPaint.setTypeface(mFont.typeface)
//        mPaint.setTextSize(mFont.size)
//        mPaint.setStyle(Style.FILL)
    }
}