package com.mshdabiola.ui

import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLayoutResult
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType

@Composable
fun MarkUpText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontStyle: FontStyle? = null,
    fontWeight: FontWeight? = null,
    fontFamily: FontFamily? = null,
    letterSpacing: TextUnit = TextUnit.Unspecified,
    textDecoration: TextDecoration? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified,
    overflow: TextOverflow = TextOverflow.Clip,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    style: TextStyle = LocalTextStyle.current,
    onTextLayout: (TextLayoutResult) -> Unit = {},
) {
    Text(
        text = buildAnnotatedString {
            // pushStyle(SpanStyle(baselineShift = BaselineShift.Subscript))
            if (MarkUpEngine.containStyle(text)) {
                text
                    .split("*")
                    .forEach {
                        when {
                            MarkUpEngine.isStyleString(it) -> pushStyle(
                                MarkUpEngine.getSpanStyle(
                                    it,
                                ),
                            )

                            it == "e" -> pop()

                            else -> append(it)
                        }
                    }
            } else {
                append(text)
            }
        },
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontStyle = fontStyle,
        fontWeight = fontWeight,
        fontFamily = fontFamily,
        letterSpacing = letterSpacing,
        textDecoration = textDecoration,
        textAlign = textAlign,
        lineHeight = lineHeight,
        overflow = overflow,
        softWrap = softWrap,
        maxLines = maxLines,
        onTextLayout = onTextLayout,
        style = style,
    )
}

object MarkUpEngine {
    private var color = Color.Unspecified
    private var weight: FontWeight? = null
    private var style: FontStyle? = null
    private var background = Color.Unspecified
    private var decoration = TextDecoration.None
    private var baselineShift = BaselineShift.None
    private var fontSize = TextUnit.Unspecified
    val list = mapOf(
        "cb" to { color = Color.Blue },
        "cr" to { color = Color.Red },
        "cy" to { color = Color.Yellow },
        "cc" to { color = Color.Cyan },
        "cm" to { color = Color.Magenta },
        "bb" to { background = Color.Blue },
        "br" to { background = Color.Red },
        "by" to { background = Color.Yellow },
        "bc" to { background = Color.Cyan },
        "bm" to { background = Color.Magenta },
        "w1" to { weight = FontWeight.Bold },
        "w2" to { weight = FontWeight.ExtraBold },
        "w3" to { weight = FontWeight.Black },
        "i" to { style = FontStyle.Italic },
        "u" to { decoration = TextDecoration.Underline },
        "l" to { decoration = TextDecoration.LineThrough },
        "sb" to {
            baselineShift = BaselineShift.Subscript
            fontSize = TextUnit(0.899f, TextUnitType.Em)
        },
        "sp" to {
            baselineShift = BaselineShift.Superscript
            fontSize = TextUnit(0.899f, TextUnitType.Em)
        },

        )

    fun containStyle(text: String): Boolean {
        return text.contains(Regex("\\*[\\w\\W\\s]+\\*[\\w\\W\\s]*\\*e\\*"))
    }

    fun isStyleString(string: String): Boolean {
        val data = string
            .split(Regex("\\s*,\\s*"))
        return list.keys.containsAll(data)
    }

    fun getSpanStyle(sty: String): SpanStyle {
        color = Color.Unspecified
        weight = null
        style = null
        background = Color.Unspecified
        decoration = TextDecoration.None
        baselineShift = BaselineShift.None
        fontSize = TextUnit.Unspecified

        val styleList = sty.split(",")
        styleList.forEach {
            list[it]?.invoke()
        }

        return SpanStyle(
            baselineShift = baselineShift,
            color = color,
            fontSize = fontSize,
            fontWeight = weight,
            fontStyle = style,
            background = background,
            textDecoration = decoration,

            )
    }
}
