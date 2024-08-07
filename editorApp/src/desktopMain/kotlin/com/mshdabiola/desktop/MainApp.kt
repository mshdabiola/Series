package com.mshdabiola.desktop

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.input.rememberTextFieldState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.WindowPlacement
import androidx.compose.ui.window.WindowPosition
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import com.mshdabiola.serieslatex.Latex
import java.util.prefs.Preferences

fun mainApp() {
    val preference = Preferences.userRoot() // .node("main")
    val isLightKey = "isLight"

    application {
        val windowState = rememberWindowState(
            size = DpSize(width = 1100.dp, height = 600.dp),
            placement = WindowPlacement.Maximized,
            position = WindowPosition.Aligned(Alignment.Center),
        )
        var isLight by remember { mutableStateOf(preference.getBoolean(isLightKey, false)) }

        Window(
            onCloseRequest = ::exitApplication,
            title = "oooo",
            icon = painterResource("launcher/system.png"),
            state = windowState,
        ) {
            var latex = "\\begin{array}{l}"
            latex += "\\u000corall\\varepsilon\\in\\mathbb{R}_+^*\\ \\exists\\eta>0\\ |x-x_0|\\leq\\eta\\Longrightarrow|f(x)-f(x_0)|\\leq\\varepsilon\\\\"
            latex += "\\det\\begin{bmatrix}a_{11}&a_{12}&\\cdots&a_{1n}\\\\a_{21}&\\ddots&&\\vdots\\\\\\vdots&&\\ddots&\\vdots\\\\a_{n1}&\\cdots&\\cdots&a_{nn}\\end{bmatrix}\\overset{\\mathrm{def}}{=}\\sum_{\\sigma\\in\\mathfrak{S}_n}\\varepsilon(\\sigma)\\prod_{k=1}^n a_{k\\sigma(k)}\\\\"
            latex += "\\sideset{_\\alpha^\\beta}{_\\gamma^\\delta}{\\begin{pmatrix}a&b\\\\c&d\\end{pmatrix}}\\\\"
            latex += "\\int_0^\\infty{x^{2n} e^{-a x^2}\\,dx} = \\u000crac{2n-1}{2a} \\int_0^\\infty{x^{2(n-1)} e^{-a x^2}\\,dx} = \\u000crac{(2n-1)!!}{2^{n+1}} \\sqrt{\\u000crac{\\pi}{a^{2n+1}}}\\\\"
            latex += "\\int_a^b{f(x)\\,dx} = (b - a) \\sum\\limits_{n = 1}^\\infty  {\\sum\\limits_{m = 1}^{2^n  - 1} {\\left( { - 1} \\right)^{m + 1} } } 2^{ - n} f(a + m\\left( {b - a} \\right)2^{-n} )\\\\"
            latex += "\\int_{-\\pi}^{\\pi} \\sin(\\alpha x) \\sin^n(\\beta x) dx = \\textstyle{\\left \\{ \\begin{array}{cc} (-1)^{(n+1)/2} (-1)^m \\u000crac{2 \\pi}{2^n} \\binom{n}{m} & n \\mbox{ odd},\\ \\alpha = \\beta (2m-n) \\\\ 0 & \\mbox{otherwise} \\\\ \\end{array} \\right .}\\\\"
            latex += "L = \\int_a^b \\sqrt{ \\left|\\sum_{i,j=1}^ng_{ij}(\\gamma(t))\\left(\\u000crac{d}{dt}x^i\\circ\\gamma(t)\\right)\\left(\\u000crac{d}{dt}x^j\\circ\\gamma(t)\\right)\\right|}\\,dt\\\\"
            latex += "\\begin{array}{rl} s &= \\int_a^b\\left\\|\\u000crac{d}{dt}\\vec{r}\\,(u(t),v(t))\\right\\|\\,dt \\\\ &= \\int_a^b \\sqrt{u'(t)^2\\,\\vec{r}_u\\cdot\\vec{r}_u + 2u'(t)v'(t)\\, \\vec{r}_u\\cdot\\vec{r}_v+ v'(t)^2\\,\\vec{r}_v\\cdot\\vec{r}_v}\\,\\,\\, dt. \\end{array}\\\\"
            latex += "\\end{array}"
//            val latex = "\\frac{4+56+56}{8+\\sqrt[3]{\\frac{3}{2}}}"
//            val latex2 = "abiola"
//            val latex3 = "\\frac{\\frac{3+5}{5}}{\\frac{65}{98}}"
            val textstate = rememberTextFieldState()

            Column(Modifier.fillMaxSize()) {
                Latex(
                    modifier = Modifier.size(300.dp),
                    text = textstate.text.toString(),
                    backgroundColor = Color.Green,
                    foregroundColor = Color.Blue,
                )
                BasicTextField(textstate, modifier = Modifier.fillMaxSize())
            }
        }
    }
}

fun main() {
    mainApp()
}
