package com.himamis.retex.renderer.android

import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.takeOrElse
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.viewinterop.AndroidView

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
    val style2= LocalTextStyle.current
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
