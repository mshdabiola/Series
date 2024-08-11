package com.mshdabiola.serieslatex

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.toAwtImage
import com.himamis.retex.renderer.desktop.FactoryProviderDesktop
import com.himamis.retex.renderer.desktop.graphics.ColorD
import com.himamis.retex.renderer.desktop.graphics.ImageD
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.graphics.Image
import java.io.ByteArrayOutputStream
import javax.imageio.ImageIO

@Composable
actual fun LoadTex(size: Double) {
    val density = androidx.compose.ui.platform.LocalDensity.current
    if (FactoryProvider.getInstance() == null) {
        FactoryProvider.setInstance(FactoryProviderDesktop())
        fontScale = size * density.density

    }
}


actual fun Color.toPaintColor(): com.himamis.retex.renderer.share.platform.graphics.Color {
    return ColorD(color = java.awt.Color(red, green, blue, alpha))
}


actual fun ImageBitmap.toByteArray(): ByteArray {
    val outputStream = ByteArrayOutputStream()
    ImageIO.write(toAwtImage(), "png", outputStream) // Adjust format as needed
    return outputStream.toByteArray()
}

actual fun Image.getImageBitmap(): ImageBitmap {
   return (this as ImageD).compose()
}