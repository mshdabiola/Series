package com.himamis.retex.renderer.android.graphics

import android.graphics.Bitmap
import android.graphics.Canvas
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image

class ImageA(width: Int, height: Int, type: Int) : Image {
    val bitmap: Bitmap

    init {
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
    }

    override fun getWidth(): Int {
        return bitmap.width
    }

    override fun getHeight(): Int {
        return bitmap.height
    }

    override fun createGraphics2D(): Graphics2DInterface {
        val canvas = Canvas(bitmap)
        return Graphics2DA(canvas)
    }

    fun compose(){

    }
}
