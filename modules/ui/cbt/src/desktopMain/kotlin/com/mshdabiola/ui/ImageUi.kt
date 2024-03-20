package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import com.mshdabiola.ui.image.AsyncImage
import com.mshdabiola.ui.image.loadImageBitmap
import com.mshdabiola.ui.image.loadSvgPainter
import com.mshdabiola.ui.image.loadXmlImageVector
import java.io.File

@Composable
actual fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale,
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
