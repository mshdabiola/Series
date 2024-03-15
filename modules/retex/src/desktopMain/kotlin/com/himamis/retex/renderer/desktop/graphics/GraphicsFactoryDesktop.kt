/**
 * This file is part of the ReTeX library - https://github.com/himamis/ReTeX
 *
 * Copyright (C) 2015 Balazs Bencze
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or (at
 * your option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but
 * WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * A copy of the GNU General Public License can be found in the file
 * LICENSE.txt provided with the source distribution of this program (see
 * the META-INF directory in the source jar). This license can also be
 * found on the GNU website at http://www.gnu.org/licenses/gpl.html.
 *
 * If you did not receive a copy of the GNU General Public License along
 * with this program, contact the lead developer, or write to the Free
 * Software Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA
 * 02110-1301, USA.
 *
 * Linking this library statically or dynamically with other modules
 * is making a combined work based on this library. Thus, the terms
 * and conditions of the GNU General Public License cover the whole
 * combination.
 *
 * As a special exception, the copyright holders of this library give you
 * permission to link this library with independent modules to produce
 * an executable, regardless of the license terms of these independent
 * modules, and to copy and distribute the resulting executable under terms
 * of your choice, provided that you also meet, for each linked independent
 * module, the terms and conditions of the license of that module.
 * An independent module is a module which is not derived from or based
 * on this library. If you modify this library, you may extend this exception
 * to your version of the library, but you are not obliged to do so.
 * If you do not wish to do so, delete this exception statement from your
 * version.
 *
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
