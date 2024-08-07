package com.mshdabiola.serieslatex

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@Composable
expect fun Latex(
    modifier: Modifier = Modifier,
    text: String,
    size: Double = 20.0,
    foregroundColor: Color = Color.Unspecified,
    backgroundColor: Color = Color.Transparent,
    style: LatexStyle = LatexStyle.DISPLAY,
    type: LatexType = LatexType.SERIF,
)
