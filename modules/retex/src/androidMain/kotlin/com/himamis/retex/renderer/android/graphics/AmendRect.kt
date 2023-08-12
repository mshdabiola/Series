package com.himamis.retex.renderer.android.graphics

import android.graphics.RectF

/**
 * Class that corrects a rectangle that is too thin to be drawn on a canvas.
 */
object AmendRect {
    /**
     * Modifies slightly the rectangle if it's too thin to be drawn on a canvas.
     * @param rectF rectangle
     * @return the same rectangle with modified bounds
     */
    fun amendRectF(rectF: RectF?): RectF? {
        if (rectF!!.bottom - rectF.top < 1.0f
            && rectF.bottom > rectF.top
        ) {
            val centerY = rectF.centerY()
            rectF.top = centerY - 0.5f
            rectF.bottom = centerY + 0.5f
        }
        if (rectF.right - rectF.left < 1.0f
            && rectF.right > rectF.left
        ) {
            val centerX = rectF.centerX()
            rectF.left = centerX - 0.5f
            rectF.right = centerX + 0.5f
        }
        return rectF
    }
}
