package com.mshdabiola.ui

import android.graphics.BitmapFactory
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import com.mshdabiola.data.SvgObject
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.io.File
import java.io.FileInputStream

actual fun loadSvgPainter(file: File, density: Density): Painter {

    val instream = ByteArrayInputStream(SvgObject.getXml(file.path).toByteArray())
    val imageVector=  instream.buffered().use { loadXmlImageVector(InputSource(it), density) }
    TODO("Not yet implemented")
}

actual fun loadImageBitmap(file: File): ImageBitmap {
    return BitmapFactory
        .decodeStream(FileInputStream(file))
        .asImageBitmap()
}

actual fun loadXmlImageVector(
    file: File,
    density: Density,
): ImageVector {
    val instream =FileInputStream(file)
    return instream.buffered().use { loadXmlImageVector(InputSource(it), density) }
}

actual fun loadSvgPainter1(
    file: File,
    density: Density,
): ImageVector {

    val instream = ByteArrayInputStream(SvgObject.getXml(file.path).toByteArray())
   return instream.buffered().use { loadXmlImageVector(InputSource(it), density) }
}