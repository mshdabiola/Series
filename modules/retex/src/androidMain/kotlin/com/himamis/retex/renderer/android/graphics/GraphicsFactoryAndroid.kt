package com.himamis.retex.renderer.android.graphics

import com.himamis.retex.renderer.share.platform.graphics.BasicStroke
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform

class GraphicsFactoryAndroid : GraphicsFactory() {
    override fun createBasicStroke(
        width: Double, cap: Int, join: Int,
        miterLimit: Double
    ): BasicStroke {
        return BasicStrokeA(width, miterLimit, cap, join)
    }

    override fun createColor(red: Int, green: Int, blue: Int, alpha: Int): Color {
        return ColorA(red, green, blue, alpha)
    }

    override fun createImage(width: Int, height: Int, type: Int): Image {
        return ImageA(width, height, type)
    }

    override fun createTransform(): Transform {
        return TransformA()
    }

    override fun createBasicStroke(width: Double, dashes: DoubleArray): Stroke {
        return BasicStrokeA(width, dashes)
    }
}
