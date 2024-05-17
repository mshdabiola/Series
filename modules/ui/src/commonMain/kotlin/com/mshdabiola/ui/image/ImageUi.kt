package com.mshdabiola.ui.image

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Density
import java.io.File

@Composable
fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit,
) {
    val density = LocalDensity.current
    val filePath = File(path)
    when (filePath.extension) {
        "svg" -> {
            AsyncImage(
                modifier = modifier,
                load = { loadSvgPainter(File(path), density) },
                painterFor = { it },
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        "xml" -> {
            AsyncImage(
                modifier = modifier,
                load = { loadXmlImageVector(File(path), density) },
                painterFor = { rememberVectorPainter(it) },
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }

        else -> {
            AsyncImage(
                modifier = modifier,
                load = { loadImageBitmap(File(path)) },
                painterFor = { BitmapPainter(it) },
                contentDescription = contentDescription,
                contentScale = contentScale,
            )
        }
    }
}

expect fun loadSvgPainter(file: File, density: Density): Painter
expect fun loadSvgPainter1(file: File, density: Density): ImageBitmap?

// fun loadSvgPainter2(file : File, density: Density) : ImageVector {
//    return loadSvgPainter1(file, density)
// }

expect fun loadXmlImageVector(file: File, density: Density): ImageVector
expect fun loadImageBitmap(file: File): ImageBitmap?
