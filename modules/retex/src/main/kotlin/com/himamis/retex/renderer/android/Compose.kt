package com.himamis.retex.renderer.android

import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
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
import androidx.compose.ui.viewinterop.AndroidView
import com.himamis.retex.renderer.android.graphics.ColorA
import com.himamis.retex.renderer.android.graphics.ImageA
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.FactoryProvider

@Composable
fun LatexView(
    modifier: Modifier = Modifier,
    text: String,
    size: Float = 20f,
    foregroundColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    style: LatexStyle = LatexStyle.DISPLAY,
    type: LatexType = LatexType.SERIF


) {
    val style2 = LocalTextStyle.current
    val textColor = foregroundColor.takeOrElse {
        style2.color.takeOrElse {
            LocalContentColor.current
        }
    }


    AndroidView(modifier = modifier, factory = {
        val laTeXView = LaTeXView(it)

        laTeXView.setForegroundColor(textColor.toArgb())
        laTeXView.setBackgroundColor(backgroundColor.toArgb())
        laTeXView.setStyle(style.value)
        laTeXView.setType(type.value)
        laTeXView.setSize(size)
        laTeXView.setLatexText(text)
        laTeXView
    })
}

@Composable
fun LatexComponen(
    modifier: Modifier = Modifier,
    text: String,
    size: Double = 20.0,
    foregroundColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    style: LatexStyle = LatexStyle.DISPLAY,
    type: LatexType = LatexType.SERIF


) {
    val style2 = LocalTextStyle.current
    val textColor = foregroundColor.takeOrElse {
        style2.color.takeOrElse {
            LocalContentColor.current
        }
    }
    val context = LocalContext.current
    val con = LocalConfiguration.current
    val density = LocalDensity.current
    var image by remember { mutableStateOf<ImageBitmap?>(null) }

    LaunchedEffect(Unit) {
        if (FactoryProvider.getInstance() == null) {
            FactoryProvider.setInstance(
                FactoryProviderAndroid(
                    context.assets
                )
            )
        }
        val size2 = size * con.fontScale * density.density
        val formula = TeXFormula(text)

        image = (formula.createBufferedImage(
            style.value, type.value,
            size2, textColor.toPaintColor(), backgroundColor.toPaintColor()
        ) as ImageA).bitmap

        println("2h ${image?.height} 2w ${image?.width}")
    }

    image?.let {
        Image(
            modifier = modifier.horizontalScroll(rememberScrollState()),
            bitmap = it,
            contentDescription = ""
        )
    }


//        SwingPanel(modifier = Modifier.size(image?.width?.dp?:4.dp,image?.height?.dp ?:4.dp), factory = {
//            val lat= LatexComponent()
//            lat
//        },
//            update = {
//                it.image=image
//            })


}


fun Color.toPaintColor(): com.himamis.retex.renderer.share.platform.graphics.Color {
    return ColorA(toArgb())
}