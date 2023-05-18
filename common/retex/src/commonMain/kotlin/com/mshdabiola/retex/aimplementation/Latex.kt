package com.mshdabiola.retex.aimplementation


import androidx.compose.foundation.Canvas
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.text.ExperimentalTextApi
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.rememberTextMeasurer
import com.mshdabiola.retex.aimplementation.graphics.ShapeUi
import kotlinx.collections.immutable.ImmutableList

@OptIn(ExperimentalTextApi::class)
@Composable
fun Latex(
    modifier: Modifier = Modifier,
    scale: Float = 2f,
    equations: ImmutableList<ShapeUi>,
    getFont: (String) -> Font
) {


    val textMeasurer = rememberTextMeasurer()
    Canvas(modifier) {
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
                    drawLine(color = Color.Black, start = Offset.Zero, end = Offset(200f, 200f))
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
}

//
//@Preview
//@Composable
//fun LatexPreview() {
//
//
//    val basic = remember { Formulae() }
//    val equations = basic.getShapes("\\sqrt[\\frac{4}{3}]{\\frac{16}{6}+78\\frac{1}{2}}")
//        .toImmutableList()
//    Latex(modifier = Modifier.size(400.dp), equations = equations) { Font(it) }
//}