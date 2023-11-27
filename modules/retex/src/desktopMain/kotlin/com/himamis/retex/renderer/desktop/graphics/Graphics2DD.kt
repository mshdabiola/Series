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

import com.himamis.retex.renderer.desktop.font.FontD
import com.himamis.retex.renderer.desktop.font.FontRenderContextD
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.geom.Line2D
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform
import java.awt.Graphics2D
import java.awt.RenderingHints
import java.awt.geom.AffineTransform
import java.awt.geom.GeneralPath
import java.util.LinkedList

class Graphics2DD(val impl: Graphics2D) : Graphics2DInterface {
    private val transformationStack = LinkedList<TransformD>()
    private var path: GeneralPath? = null
    override fun setStroke(stroke: Stroke) {
        impl.stroke = stroke as java.awt.Stroke
    }

    override fun getStroke(): Stroke {
        return StrokeD(impl.stroke)
    }

    override fun setColor(color: Color) {
        impl.color = color as java.awt.Color
    }

    override fun getColor(): Color {
        return ColorD(impl.color)
    }

    override fun getTransform(): Transform {
        return TransformD(impl.transform)
    }

    override fun getFont(): Font {
        return FontD(impl.font)
    }

    override fun setFont(font: Font) {
        impl.font = (font as FontD).font
    }

    override fun fillRect(x: Double, y: Double, width: Double, height: Double) {
        impl.fillRect(x.toInt(), y.toInt(), width.toInt(), height.toInt())
    }

    override fun fill(s: Shape) {
        impl.fill(s as java.awt.Shape)
    }

    override fun startDrawing() {
        path = GeneralPath()
    }

    override fun moveTo(x: Double, y: Double) {
        path!!.moveTo(x, y)
    }

    override fun lineTo(x: Double, y: Double) {
        path!!.lineTo(x, y)
    }

    override fun quadraticCurveTo(x: Double, y: Double, x1: Double, y1: Double) {
        path!!.quadTo(x, y, x1, y1)
    }

    override fun bezierCurveTo(
        x: Double, y: Double, x1: Double, y1: Double,
        x2: Double, y2: Double,
    ) {
        path!!.curveTo(x, y, x1, y1, x2, y2)
    }

    override fun finishDrawing() {
        impl.fill(path)
    }

    override fun draw(rectangle: Rectangle2D) {
        impl.draw(rectangle as java.awt.Shape)
    }

    override fun draw(rectangle: RoundRectangle2D) {
        impl.draw(rectangle as java.awt.Shape)
    }

    override fun draw(line: Line2D) {
        impl.draw(line as java.awt.Shape)
    }

    override fun drawChars(data: CharArray, offset: Int, length: Int, x: Int, y: Int) {
        impl.drawChars(data, offset, length, x, y)
    }

    override fun drawArc(
        x: Int, y: Int, width: Int, height: Int, startAngle: Int,
        arcAngle: Int,
    ) {
        impl.drawArc(x, y, width, height, startAngle, arcAngle)
    }

    override fun fillArc(
        x: Int, y: Int, width: Int, height: Int, startAngle: Int,
        arcAngle: Int,
    ) {
        impl.fillArc(x, y, width, height, startAngle, arcAngle)
    }

    override fun translate(x: Double, y: Double) {
        impl.translate(x, y)
    }

    override fun scale(x: Double, y: Double) {
        impl.scale(x, y)
    }

    override fun rotate(theta: Double, x: Double, y: Double) {
        impl.rotate(theta, x, y)
    }

    override fun rotate(theta: Double) {

        impl.rotate(theta)
    }


    override fun drawImage(image: Image, x: Int, y: Int) {
        impl.drawImage(image as java.awt.Image, x, y, null)
    }

    override fun drawImage(image: Image, transform: Transform) {
        impl.drawImage(
            image as java.awt.Image, transform as AffineTransform,
            null
        )
    }

    override fun getFontRenderContext(): FontRenderContext {
        return FontRenderContextD(impl.fontRenderContext)
    }

    override fun dispose() {
        impl.dispose()
    }

    override fun setRenderingHint(key: Int, value: Int) {
        impl.setRenderingHint(
            getNativeRenderingKey(key),
            getNativeRenderingValue(value)
        )
    }

    override fun getRenderingHint(key: Int): Int {
        val nKey = getNativeRenderingKey(key)
        val `val` = impl.getRenderingHint(nKey)
        return getRenderingValue(`val`)
    }

    override fun saveTransformation() {
        transformationStack.add(TransformD(impl.transform))
    }

    override fun restoreTransformation() {
        val last = transformationStack.removeLast()
        impl.transform = last
    }

    companion object {
        private fun getNativeRenderingKey(key: Int): RenderingHints.Key? {
            return when (key) {
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.KEY_ANTIALIASING -> RenderingHints.KEY_ANTIALIASING
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.KEY_RENDERING -> RenderingHints.KEY_RENDERING
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.KEY_TEXT_ANTIALIASING -> RenderingHints.KEY_TEXT_ANTIALIASING
                else -> null
            }
        }

        private fun getNativeRenderingValue(value: Int): Any? {
            return when (value) {
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_ANTIALIAS_ON -> RenderingHints.VALUE_ANTIALIAS_ON
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_RENDER_QUALITY -> RenderingHints.VALUE_RENDER_QUALITY
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_TEXT_ANTIALIAS_ON -> RenderingHints.VALUE_TEXT_ANTIALIAS_ON
                else -> null
            }
        }

        private fun getRenderingValue(value: Any): Int {
            return if (value === RenderingHints.VALUE_INTERPOLATION_BICUBIC) {
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_INTERPOLATION_BICUBIC
            } else if (value === RenderingHints.VALUE_INTERPOLATION_BILINEAR) {
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_INTERPOLATION_BILINEAR
            } else if (value === RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR) {
                com.himamis.retex.renderer.share.platform.graphics.RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR
            } else {
                -1
            }
        }
    }
}
