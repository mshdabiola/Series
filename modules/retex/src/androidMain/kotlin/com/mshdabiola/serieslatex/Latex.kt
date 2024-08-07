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
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import com.himamis.retex.renderer.android.FactoryProviderAndroid
import com.himamis.retex.renderer.android.graphics.ColorA
import com.himamis.retex.renderer.android.graphics.ImageA
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.FactoryProvider

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
    val style2 = LocalTextStyle.current
    val color = LocalContentColor.current
    val textColor = remember(color, foregroundColor, style2) {
        foregroundColor.takeOrElse {
            style2.color.takeOrElse {
                color
            }
        }
    }
    val context = LocalContext.current
    val con = LocalConfiguration.current
    val density = LocalDensity.current
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
    var error by remember {
        mutableStateOf<String?>(null)
    }

    LaunchedEffect(text) {
        error = null
        try {
            if (FactoryProvider.getInstance() == null) {
                FactoryProvider.setInstance(
                    FactoryProviderAndroid(
                        context.assets,
                    ),
                )
            }
            val size2 = size * con.fontScale * density.density
            val formula = TeXFormula(text)

            image = (
                formula.createBufferedImage(
                    style.value,
                    type.value,
                    size2,
                    textColor.toPaintColor(),
                    backgroundColor.toPaintColor(),
                ) as ImageA
                ).bitmap
        } catch (e: Exception) {
            error = e.message
            image = null
        }
    }

    image?.let {
        Image(
            modifier = modifier.horizontalScroll(rememberScrollState()),
            bitmap = it,
            contentDescription = "",
        )
    }

    error?.let {
        Text(it, color = MaterialTheme.colorScheme.error)
    }
}

fun Color.toPaintColor(): com.himamis.retex.renderer.share.platform.graphics.Color {
    return ColorA(toArgb())
}

@Composable
fun Testing(modifier: Modifier = Modifier) {
    Text("Hello World")
}
