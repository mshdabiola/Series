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

import android.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asAndroidBitmap
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image

class ImageA(width: Int, height: Int, type: Int) : Image {
    val bitmap: ImageBitmap

    init {
        bitmap = ImageBitmap(width, height, ImageBitmapConfig.Argb8888)
    }

    override fun getWidth(): Int {
        return bitmap.width
    }

    override fun getHeight(): Int {
        return bitmap.height
    }

    override fun createGraphics2D(): Graphics2DInterface {
        val canvas = Canvas(bitmap.asAndroidBitmap())
        return Graphics2DA(canvas)
    }
}
