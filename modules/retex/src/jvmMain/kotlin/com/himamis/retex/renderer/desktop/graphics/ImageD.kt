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
package com.himamis.retex.renderer.desktop.graphics

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toComposeImageBitmap
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image
import java.awt.image.BufferedImage

class ImageD : BufferedImage, Image {

    constructor(image: BufferedImage) : super(
        image.colorModel,
        image.copyData(null),
        image.isAlphaPremultiplied,
        null,

    )

    constructor(width: Int, height: Int, imageType: Int) : super(width, height, imageType)

    override fun createGraphics2D(): Graphics2DInterface {
        return Graphics2DD(createGraphics())
    }

    fun compose(): ImageBitmap {
        return toComposeImageBitmap()
    }
}
