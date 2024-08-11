package com.himamis.retex.renderer.android.graphics

import android.graphics.Bitmap
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
