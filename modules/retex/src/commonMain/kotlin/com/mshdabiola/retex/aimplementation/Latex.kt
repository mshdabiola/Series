package com.mshdabiola.retex.aimplementation


import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.ui.unit.dp
import com.mshdabiola.retex.aimplementation.graphics.ShapeUi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

@OptIn(ExperimentalTextApi::class)
@Composable
fun LatexText(
    modifier: Modifier = Modifier,
    equationsStr: String,
    scale: Float = 1.5f,
    getFont: (String) -> Font,
) {


    val textMeasurer = rememberTextMeasurer()
    val density = LocalDensity.current.density

    var equations by remember(equationsStr) {
        mutableStateOf(emptyList<ShapeUi>())
    }
    var errorMessage by remember(equationsStr) {
        mutableStateOf<String?>(null)
    }

    val height by remember {
        derivedStateOf {
            val h = equations.maxOfOrNull {
                it.topLeft.y
            }?.plus(20) ?: 10f
            ((h * scale / density)).toInt().dp
        }
    }
    val width by remember {
        derivedStateOf {
            val w = equations.maxOfOrNull {
                it.topLeft.x
            }?.plus(20) ?: 10f
            ((w * scale / density)).toInt().dp
        }
    }

    LaunchedEffect(equations) {
        withContext(Dispatchers.Default) {
            try {
                errorMessage = null
                equations = Formulae.getShapes(density.toDouble(), equationsStr)
            } catch (e: Exception) {
                errorMessage = e.message
            }

        }
    }

    Box(modifier = modifier) {
        if (errorMessage == null) {
            Canvas(Modifier.size(width, height).align(Alignment.Center)) {
                scale(scale, pivot = Offset.Zero) {
                    equations.forEach {
                        if (it is ShapeUi.Text) {


                            scale(it.scale, pivot = it.topLeft) {
                                drawText(
                                    //size= it.size,
                                    textMeasurer = textMeasurer,
                                    text = it.text,
                                    topLeft = it.topLeft,
                                    style = TextStyle.Default.copy(
                                        fontSize = TextStyle.Default.fontSize,
                                        fontFamily = FontFamily(getFont(it.fontPath))
                                    )
                                )
                            }
                        }
                        if (it is ShapeUi.Line) {
                            drawLine(
                                color = Color.Black,
                                start = Offset.Zero,
                                end = Offset(200f, 200f)
                            )
                        }
                        if (it is ShapeUi.Rectangle) {

                            drawRect(
                                Color.Black,
                                topLeft = it.topLeft,
                                size = it.size,
                                style = if (it.isFill) Fill else Stroke(0.4f)
                            )
                            //drawLine(color = Color.Black, start = Offset.Zero, end = Offset(200f,200f))
                        }
                    }
                }

            }
        } else {
            Text("Error: $errorMessage", modifier = Modifier)
        }

    }
}