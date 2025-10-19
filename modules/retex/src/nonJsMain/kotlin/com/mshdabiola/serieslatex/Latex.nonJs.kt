/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
actual fun Latex(
    modifier: Modifier,
    text: String,
    size: Double,
    foregroundColor: Color,
    backgroundColor: Color,
    style: LatexStyle,
    type: LatexType,
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
