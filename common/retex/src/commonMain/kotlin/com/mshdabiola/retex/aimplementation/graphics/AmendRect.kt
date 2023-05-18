package com.mshdabiola.retex.aimplementation.graphics

import androidx.compose.ui.geometry.Rect


/**
 * Class that corrects a rectangle that is too thin to be drawn on a canvas.
 */
object AmendRect {
    /**
     * Modifies slightly the rectangle if it's too thin to be drawn on a canvas.
     * @param rectF rectangle
     * @return the same rectangle with modified bounds
     */
    fun amendRectF(rectF: Rect): Rect {
        return when {
            rectF.bottom - rectF.top < 1.0f
                    && rectF.bottom > rectF.top -> {
                val centerY: Float = rectF.center.y
                rectF.copy(top = centerY - 0.5f, bottom = centerY + 0.5f)

            }

            rectF.right - rectF.left < 1.0f
                    && rectF.right > rectF.left -> {
                val centerX: Float = rectF.center.x
                rectF.copy(left = centerX - 0.5f, right = centerX + 0.5f)
            }

            else -> rectF
        }

    }
}