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

import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.graphics.BasicStroke
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform
import java.awt.image.BufferedImage
import java.io.ByteArrayInputStream
import java.io.File
import java.io.IOException
import java.net.URL
import javax.imageio.ImageIO

class GraphicsFactoryDesktop : GraphicsFactory() {
    override fun createBasicStroke(
        width: Double,
        cap: Int,
        join: Int,
        miterLimit: Double,
    ): BasicStroke {
        return BasicStrokeD(width, cap, join, miterLimit)
    }

    override fun createColor(red: Int, green: Int, blue: Int, alpha: Int): Color {
        return ColorD(red, green, blue, alpha)
    }

    override fun createImage(width: Int, height: Int, type: Int): Image {
        return ImageD(width, height, type)
    }

    override fun createTransform(): Transform {
        return TransformD()
    }

    override fun createImage(base64: String, width: Int, height: Int): Image? {
        var pngBase64 = base64
        val pngMarker = "data:image/png;base64,"
        pngBase64 = if (pngBase64.startsWith(pngMarker)) {
            pngBase64.substring(pngMarker.length)
        } else {
            FactoryProvider.debugS("invalid base64 image")
            return null
        }
        val imageData = Base64.decode(pngBase64)
        try {
            return ImageD(ImageIO.read(ByteArrayInputStream(imageData)))
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun createImage(path: String): Image? {
        val bimage: BufferedImage
        if (path.startsWith("https://")) {
            return try {
                val url = URL(path)
                bimage = ImageIO.read(url)
                ImageD(bimage)
            } catch (e: Exception) {
                // MalformedURLException
                // IOException
                e.printStackTrace()
                null
            }
        }
        val f = File(path)
        return try {
            bimage = ImageIO.read(f)
            ImageD(bimage)
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    override fun createBasicStroke(width: Double, dashes: DoubleArray): Stroke {
        return BasicStrokeD(width, dashes)
    }
}
