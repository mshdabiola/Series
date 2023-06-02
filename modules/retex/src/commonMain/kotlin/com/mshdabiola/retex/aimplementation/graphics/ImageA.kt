package com.mshdabiola.retex.aimplementation.graphics


import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image

class ImageA(val widthm: Int, val heightm: Int, type: Int) : Image {
    override fun getWidth(): Int {
        return widthm
    }

    override fun getHeight(): Int {
        return heightm
    }
    // private val mBitmap: Bitmap


    override fun createGraphics2D(): Graphics2DInterface {
        return Graphics2DA()
    }


}