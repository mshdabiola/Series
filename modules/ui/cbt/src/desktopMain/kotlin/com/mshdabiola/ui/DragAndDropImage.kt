package com.mshdabiola.ui

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.awt.ComposePanel
import androidx.compose.ui.awt.SwingPanel
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.image.AsyncImage
import com.mshdabiola.ui.image.loadImageBitmap
import com.mshdabiola.ui.image.loadSvgPainter
import com.mshdabiola.ui.image.loadXmlImageVector
import java.awt.dnd.DnDConstants
import java.awt.dnd.DropTarget
import java.awt.dnd.DropTargetDragEvent
import java.awt.dnd.DropTargetDropEvent
import java.awt.dnd.DropTargetEvent
import java.awt.dnd.DropTargetListener
import java.io.File

@Composable
actual fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
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
                contentScale = contentScale
            )
        }

        "xml" -> {
            AsyncImage(
                modifier = modifier,
                load = { loadXmlImageVector(File(path), density) },
                painterFor = { rememberVectorPainter(it) },
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }

        else -> {
            AsyncImage(
                modifier = modifier,
                load = { loadImageBitmap(File(path)) },
                painterFor = { BitmapPainter(it) },
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }

    }

}