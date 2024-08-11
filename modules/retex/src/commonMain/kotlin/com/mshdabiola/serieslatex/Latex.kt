package com.mshdabiola.serieslatex

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.layout.ContentScale
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.graphics.Image

var fontScale = 1.0

@Composable
fun Latex(
    modifier: Modifier = Modifier,
    text: String,
    size: Double = 20.0,
    foregroundColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    style: LatexStyle = LatexStyle.DISPLAY,
    type: LatexType = LatexType.SERIF,
) {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    val style2 = LocalTextStyle.current
    val color = LocalContentColor.current
    val textColor = remember(color, foregroundColor, style2) {
        foregroundColor.takeOrElse {
            style2.color.takeOrElse {
                color
            }
        }
    }

    var error by remember {
        mutableStateOf<String?>(null)
    }
    LoadTex(size)

    LaunchedEffect(text) {
        error = null
        try {
            image = getLatexImage(
                text,
                textColor,
                backgroundColor,
                style,
                type,
            )


        } catch (e: Exception) {
            error = e.message
            image = null
        }
    }

    image?.let {
        Image(
            modifier = modifier
                .horizontalScroll(rememberScrollState()),
            bitmap = it,
            contentDescription = "",
            contentScale = ContentScale.Fit,
        )
    }
    error?.let {
        Text(it, color = MaterialTheme.colorScheme.error)
    }
}

fun getLatexImage(
    text: String,
    foregroundColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    style: LatexStyle = LatexStyle.DISPLAY,
    type: LatexType = LatexType.SERIF,
): ImageBitmap {
    val formula = TeXFormula(text)


    return formula.createBufferedImage(
        style.value,
        type.value,
        fontScale,
        foregroundColor.toPaintColor(),
        backgroundColor.toPaintColor(),
    ).getImageBitmap()

}

expect fun Color.toPaintColor(): com.himamis.retex.renderer.share.platform.graphics.Color

@Composable
expect fun LoadTex(size: Double = 20.0)

expect fun ImageBitmap.toByteArray(): ByteArray

expect fun Image.getImageBitmap(): ImageBitmap
