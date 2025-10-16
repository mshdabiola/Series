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
        if (rectF!!.bottom - rectF.top < 1.0f &&
            rectF.bottom > rectF.top
        ) {
            val centerY = rectF.centerY()
            rectF.top = centerY - 0.5f
            rectF.bottom = centerY + 0.5f
        }
        if (rectF.right - rectF.left < 1.0f &&
            rectF.right > rectF.left
        ) {
            val centerX = rectF.centerX()
            rectF.left = centerX - 0.5f
            rectF.right = centerX + 0.5f
        }
        return rectF
    }
}
