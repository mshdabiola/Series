package com.mshdabiola.ui.image

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.produceState
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.singleWindowApplication
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.xml.sax.InputSource
import java.io.File
import java.io.IOException
import java.net.URL

fun main() = singleWindowApplication {
    val density = LocalDensity.current
    Column {
        AsyncImage(
            load = { loadImageBitmap(File("sample.png")) },
            painterFor = { remember { BitmapPainter(it) } },
            contentDescription = "Sample",
            modifier = Modifier.width(200.dp)
        )
        AsyncImage(
            load = {
                loadSvgPainter(
                    "https://github.com/JetBrains/compose-multiplatform/raw/master/artwork/idea-logo.svg",
                    density
                )
            },
            painterFor = { it },
            contentDescription = "Idea logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(200.dp)
        )
        AsyncImage(
            load = { loadXmlImageVector(File("compose-logo.xml"), density) },
            painterFor = { rememberVectorPainter(it) },
            contentDescription = "Compose logo",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.width(200.dp)
        )
    }
}

@Composable
fun <T> AsyncImage(
    load: () -> T,
    painterFor: @Composable (T) -> Painter,
    contentDescription: String,
    modifier: Modifier = Modifier,
    contentScale: ContentScale = ContentScale.Fit,
) {

    var image by remember { mutableStateOf<T?>(null) }

    LaunchedEffect(load) {
        image= withContext(Dispatchers.IO) {
            try {
                load()
            } catch (e: IOException) {
                // instead of printing to console, you can also write this to log,
                // or show some error placeholder
                e.printStackTrace()
                null
            }
        }
    }

    if (image != null) {
        Image(
            painter = painterFor(image!!),
            contentDescription = contentDescription,
            contentScale = contentScale,
            modifier = modifier
        )
    }
}

@Composable
fun DesktopImage(
    modifier: Modifier = Modifier,
    path:String,
    contentDescription: String,
    contentScale: ContentScale = ContentScale.Fit
) {
    val density= LocalDensity.current
    val filePath=File(path)
    when(filePath.extension){
        "svg"->{
            AsyncImage(
                modifier=modifier,
                load = { loadSvgPainter(File(path),density) },
                painterFor = {it},
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
        "xml"->{
            AsyncImage(
                modifier=modifier,
                load = { loadXmlImageVector(File(path),density) },
                painterFor = { rememberVectorPainter( it)},
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }
        else->{
            AsyncImage(
                modifier=modifier,
                load = { loadImageBitmap(File(path)) },
                painterFor = { BitmapPainter( it) },
                contentDescription = contentDescription,
                contentScale = contentScale
            )
        }

    }

}
/* Loading from file with java.io API */

fun loadImageBitmap(file: File): ImageBitmap =
    file.inputStream().buffered().use(::loadImageBitmap)

fun loadSvgPainter(file: File, density: Density): Painter =
    file.inputStream().buffered().use { loadSvgPainter(it, density) }

fun loadXmlImageVector(file: File, density: Density): ImageVector =
    file.inputStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

/* Loading from network with java.net API */

fun loadImageBitmap(url: String): ImageBitmap =
    URL(url).openStream().buffered().use(::loadImageBitmap)

fun loadSvgPainter(url: String, density: Density): Painter =
    URL(url).openStream().buffered().use { loadSvgPainter(it, density) }

fun loadXmlImageVector(url: String, density: Density): ImageVector =
    URL(url).openStream().buffered().use { loadXmlImageVector(InputSource(it), density) }

/* Loading from network with Ktor client API (https://ktor.io/docs/client.html). */

/*

suspend fun loadImageBitmap(url: String): ImageBitmap =
    urlStream(url).use(::loadImageBitmap)

suspend fun loadSvgPainter(url: String, density: Density): Painter =
    urlStream(url).use { loadSvgPainter(it, density) }

suspend fun loadXmlImageVector(url: String, density: Density): ImageVector =
    urlStream(url).use { loadXmlImageVector(InputSource(it), density) }

@OptIn(KtorExperimentalAPI::class)
private suspend fun urlStream(url: String) = HttpClient(CIO).use {
    ByteArrayInputStream(it.get(url))
}

 */

@Composable
fun <T> produceState(
    initialValue: T,
     path: String
): State<T> {
    val result = remember { mutableStateOf(initialValue) }
    LaunchedEffect(path) {

    }
    return result
}