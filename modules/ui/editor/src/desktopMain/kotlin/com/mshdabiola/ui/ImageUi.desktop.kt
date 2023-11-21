package com.mshdabiola.ui

import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.unit.Density
import com.mshdabiola.data.SvgObject
import org.xml.sax.InputSource
import java.io.ByteArrayInputStream
import java.io.File
import java.net.URL


actual fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)

actual fun loadSvgPainter(file: File, density: Density): Painter =
    file.inputStream().buffered().use { androidx.compose.ui.res.loadSvgPainter(it, density) }

actual fun loadXmlImageVector(file: File, density: Density): ImageVector =
    file.inputStream().buffered().use {
        androidx.compose.ui.res.loadXmlImageVector(
            InputSource(it),
            density
        )
    }

/* Loading from network with java.net API */

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { androidx.compose.ui.res.loadSvgPainter(it, density) }

fun loadXmlImageVector(url: String, density: Density): ImageVector =
    URL(url).openStream().buffered().use {
        androidx.compose.ui.res.loadXmlImageVector(
            InputSource(it),
            density
        )
    }

actual fun loadSvgPainter1(
    file: File,
    density: Density,
): ImageVector {
    val instream = ByteArrayInputStream(SvgObject.getXml(file.path).toByteArray())
    return instream.buffered().use {
        androidx.compose.ui.res.loadXmlImageVector(
            InputSource(it),
            density
        )
    }
}