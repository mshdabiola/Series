package com.himamis.retex.renderer.desktop

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import com.himamis.retex.renderer.desktop.graphics.ImageD
import com.himamis.retex.renderer.share.Colors
import com.himamis.retex.renderer.share.TeXConstants
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.FactoryProvider


@Preview
@Composable
fun LatexComponen() {
    var image by remember { mutableStateOf<ImageBitmap?>(null) }
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
    LaunchedEffect(Unit) {
        if (FactoryProvider.getInstance() == null) {
            FactoryProvider.setInstance(FactoryProviderDesktop())
        }
        val formula = TeXFormula(latex)

        image = (formula.createBufferedImage(
            TeXConstants.STYLE_DISPLAY,
            50.0, Colors.BLACK, Colors.WHITE
        ) as ImageD).compose()

        println("2h ${image?.height} 2w ${image?.width}")
    }

    image?.let {
        Image(
            modifier = Modifier.horizontalScroll(rememberScrollState()),
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