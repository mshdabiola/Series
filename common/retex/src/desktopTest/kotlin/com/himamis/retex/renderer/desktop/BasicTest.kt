package com.himamis.retex.renderer.desktop

//import com.mshdabiola.retex.ColorUtil

import com.himamis.retex.renderer.share.ColorUtil
import com.himamis.retex.renderer.share.TeXConstants
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.TeXIcon
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Insets
import com.mshdabiola.retex.aimplementation.Formulae
import com.mshdabiola.retex.aimplementation.graphics.Graphics2DA
import com.mshdabiola.retex.aimplementation.graphics.ImageA
import org.junit.Before
import org.junit.Test
import org.koin.test.KoinTest
import java.awt.image.BufferedImage
import java.io.ByteArrayOutputStream
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import javax.imageio.ImageIO

class BasicTest : KoinTest {

    @Before
    fun setFactory() {
        //  FactoryProvider.setInstance( FactoryProviderAndroid(File("")))
    }

    @Test
    fun basicExample2() {
//        var latex = "\\begin{array}{l}"
//        latex += "\\u000corall\\varepsilon\\in\\mathbb{R}_+^*\\ \\exists\\eta>0\\ |x-x_0|\\leq\\eta\\Longrightarrow|f(x)-f(x_0)|\\leq\\varepsilon\\\\"
//        latex += "\\det\\begin{bmatrix}a_{11}&a_{12}&\\cdots&a_{1n}\\\\a_{21}&\\ddots&&\\vdots\\\\\\vdots&&\\ddots&\\vdots\\\\a_{n1}&\\cdots&\\cdots&a_{nn}\\end{bmatrix}\\overset{\\mathrm{def}}{=}\\sum_{\\sigma\\in\\mathfrak{S}_n}\\varepsilon(\\sigma)\\prod_{k=1}^n a_{k\\sigma(k)}\\\\"
//        latex += "\\sideset{_\\alpha^\\beta}{_\\gamma^\\delta}{\\begin{pmatrix}a&b\\\\c&d\\end{pmatrix}}\\\\"
//        latex += "\\int_0^\\infty{x^{2n} e^{-a x^2}\\,dx} = \\u000crac{2n-1}{2a} \\int_0^\\infty{x^{2(n-1)} e^{-a x^2}\\,dx} = \\u000crac{(2n-1)!!}{2^{n+1}} \\sqrt{\\u000crac{\\pi}{a^{2n+1}}}\\\\"
//        latex += "\\int_a^b{f(x)\\,dx} = (b - a) \\sum\\limits_{n = 1}^\\infty  {\\sum\\limits_{m = 1}^{2^n  - 1} {\\left( { - 1} \\right)^{m + 1} } } 2^{ - n} f(a + m\\left( {b - a} \\right)2^{-n} )\\\\"
//        latex += "\\int_{-\\pi}^{\\pi} \\sin(\\alpha x) \\sin^n(\\beta x) dx = \\textstyle{\\left \\{ \\begin{array}{cc} (-1)^{(n+1)/2} (-1)^m \\u000crac{2 \\pi}{2^n} \\binom{n}{m} & n \\mbox{ odd},\\ \\alpha = \\beta (2m-n) \\\\ 0 & \\mbox{otherwise} \\\\ \\end{array} \\right .}\\\\"
//        latex += "L = \\int_a^b \\sqrt{ \\left|\\sum_{i,j=1}^ng_{ij}(\\gamma(t))\\left(\\u000crac{d}{dt}x^i\\circ\\gamma(t)\\right)\\left(\\u000crac{d}{dt}x^j\\circ\\gamma(t)\\right)\\right|}\\,dt\\\\"
//        latex += "\\begin{array}{rl} s &= \\int_a^b\\left\\|\\u000crac{d}{dt}\\vec{r}\\,(u(t),v(t))\\right\\|\\,dt \\\\ &= \\int_a^b \\sqrt{u'(t)^2\\,\\vec{r}_u\\cdot\\vec{r}_u + 2u'(t)v'(t)\\, \\vec{r}_u\\cdot\\vec{r}_v+ v'(t)^2\\,\\vec{r}_v\\cdot\\vec{r}_v}\\,\\,\\, dt. \\end{array}\\\\"
//        latex += "\\end{array}"
        val latex = "\\frac{4+56+56}{8+\\sqrt[3]{\\frac{3}{2}}}"
        val latex2 = "abiola"
        val latex3 = "\\frac{\\frac{3+5}{5}}{\\frac{65}{98}}"

        val formulae = Formulae()

        formulae.getShapes(latex)
            .forEach { println(it) }

        // doTest(latex3, "Basic/Example2")
    }

    private fun doTest(latex: String, exampleName: String) {
        val icon = createTeXIcon(latex)
        val image = createImage(icon)
//        val imageBytes = getImageBytes(image)
//        val expectedBytes = loadResourceFile("$exampleName.png")
//        Assert.assertArrayEquals(expectedBytes, imageBytes)
    }

    private fun createTeXIcon(latex: String): TeXIcon {
        var formula: TeXFormula? = null
        try {
            formula = TeXFormula(latex)
        } catch (e: Throwable) {
            e.printStackTrace()
        }
        val icon = formula!!.TeXIconBuilder()
            .setStyle(TeXConstants.STYLE_DISPLAY)
            .setSize(200.0)
            .build()
        icon.insets = Insets(5, 5, 5, 5)
        return icon
    }

    private fun createImage(icon: TeXIcon): Image {
        val image: Image = ImageA(200, 200, Image.TYPE_INT_ARGB)
        val g2 = image.createGraphics2D()
        g2!!.color = ColorUtil.WHITE
        g2.fillRect(0.0, 0.0, 200.0, 200.0)
        icon.paintIcon(null, g2, 0.0, 0.0)
        println((g2 as Graphics2DA).shape.joinToString())
        return image
    }

    private fun getImageBytes(image: Image): ByteArray {
        val os = ByteArrayOutputStream()
        try {
            ImageIO.write(image as BufferedImage, "png", os)
        } catch (ex: IOException) {
        }
        return os.toByteArray()
    }

    private fun loadResourceFile(name: String): ByteArray {
        val path = ClassLoader.getSystemResource(name).file.substring(1)
        return try {
            Files.readAllBytes(Paths.get(path))
        } catch (e: IOException) {
            ByteArray(1)
        }
    }
}
