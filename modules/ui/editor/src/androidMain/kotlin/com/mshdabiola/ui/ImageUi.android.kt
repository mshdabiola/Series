package com.mshdabiola.ui

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import com.caverock.androidsvg.SVG
import org.xml.sax.InputSource
import java.io.File
import java.io.FileInputStream

actual fun loadSvgPainter(file: File, density: Density): Painter {

    val inputStream = FileInputStream(file)
    val svg = SVG.getFromInputStream(inputStream)
    val w = if (svg.documentWidth > 0) svg.documentWidth else svg.documentViewBox.width()
    val h = if (svg.documentHeight > 0) svg.documentHeight else svg.documentViewBox.height()

    svg.renderDPI = density.density * 96f // default is 96


    val bitmap = ImageBitmap(w.toInt(), h.toInt(), ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)
    canvas.nativeCanvas.drawRGB(255, 255, 255)
    svg.renderToCanvas(canvas.nativeCanvas)


    return BitmapPainter(bitmap)

}

actual fun loadImageBitmap(file: File): ImageBitmap? {
    return try {
        BitmapFactory
            .decodeStream(FileInputStream(file))
            .asImageBitmap()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }
}

actual fun loadXmlImageVector(
    file: File,
    density: Density,
): ImageVector {
    val instream = FileInputStream(file)
    return instream.buffered().use { loadXmlImageVector(InputSource(it), density) }
}

actual fun loadSvgPainter1(
    file: File,
    density: Density,
): ImageBitmap? {
    val instream = FileInputStream(file)
    val svg = SVG.getFromInputStream(instream)
    //if (svg.documentWidth.toInt() !=-1){
    val w = svg.documentViewBox.width()
    val h = svg.documentViewBox.height()
    svg.renderDPI = density.density * 96f // default is 96


    val bitmap = ImageBitmap(w.toInt(), h.toInt(), ImageBitmapConfig.Argb8888)
    val canvas = Canvas(bitmap)
    canvas.nativeCanvas.drawRGB(255, 255, 255)
//         canvas.nativeCanvas.scale(density.density,density.density)
    svg.renderToCanvas(canvas.nativeCanvas)

    return bitmap
    // }else null
}