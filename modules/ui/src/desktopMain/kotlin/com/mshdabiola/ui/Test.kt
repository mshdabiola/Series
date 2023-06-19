package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.ResourceLoader
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.loadSvgPainter
import androidx.compose.ui.res.loadXmlImageVector
import androidx.compose.ui.res.painterResource
import org.xml.sax.InputSource
import java.io.InputStream

@OptIn(ExperimentalComposeUiApi::class)
@Composable
@Preview
fun Test(
) {

    Icon(painterResource("aaa.xml") ,"", tint = Color.Blue)
}

@OptIn(ExperimentalComposeUiApi::class)
internal inline fun <T> useResource(
    resourcePath: String,
    loader: ResourceLoader,
    block: (InputStream) -> T
): T = openResource(resourcePath, loader).use(block)

@PublishedApi
@ExperimentalComposeUiApi
internal fun openResource(
    resourcePath: String,
    loader: ResourceLoader
): InputStream {
    return loader.load(resourcePath)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loadXml(path:String):ImageVector{
    val density= LocalDensity.current
    return remember(path, density) {
        useResource(path,  ResourceLoader.Default) {
            loadXmlImageVector(InputSource(it), density)

        }
    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loadImage(path:String):ImageBitmap{
    return remember(path) {
        useResource(path,  ResourceLoader.Default) {
            loadImageBitmap(it)
        }

    }
}
@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun loadSvg(path:String): Painter {
    val density= LocalDensity.current
    return remember(path, density) {
        useResource(path,  ResourceLoader.Default) {
           loadSvgPainter(it, density)

        }
    }
}