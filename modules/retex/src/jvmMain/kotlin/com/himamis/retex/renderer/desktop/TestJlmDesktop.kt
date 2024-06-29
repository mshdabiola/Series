package com.himamis.retex.renderer.desktop

import com.himamis.retex.renderer.desktop.graphics.Graphics2DD
import com.himamis.retex.renderer.share.Colors
import com.himamis.retex.renderer.share.Configuration
import com.himamis.retex.renderer.share.TeXConstants
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.FactoryProvider
import java.awt.Graphics
import java.awt.Graphics2D
import javax.swing.JFrame
import javax.swing.JPanel

class TestJlmDesktop : JFrame() {
    init {
        val panel = JPanel()
        contentPane.add(panel)
        setSize(1000, 1000)
    }

    override fun paint(g: Graphics) {
        super.paint(g)
        val g2 = g as Graphics2D
        val g2d = Graphics2DD(g2)
        val texts = arrayOf(
            """
                 \begin{tabular}{|l|l|l|}
                 Rows  & Column 1 & Column 2 \\Row 1 & 1234     & 2345     \\Row 2 & 3456     & 4567     \\Row 3 & 5678     & 6789     \\Row 4 & 7890     & 8901     \\Row 5 & 9012     & 10000    \\\end{tabular}
            """.trimIndent(),
            "\\renewcommand{\\arraystretch}{1.7}" +
                "\\begin{tabular}{|l|l|l|}" +
                "Rows  & Column 1 & Column 2 \\\\" +
                "Row 1 & 1234     & 2345     \\\\" +
                "Row 2 & 3456     & 4567     \\\\" +
                "Row 3 & 5678     & 6789     \\\\" +
                "Row 4 & 7890     & 8901     \\\\" +
                "Row 5 & 9012     & 10000    \\\\" +
                "\\end{tabular}",
        )
        var y = 100
        for (text in texts) {
            val formula = TeXFormula(text)
            val im = formula.createBufferedImage(
                TeXConstants.STYLE_DISPLAY,
                30.0,
                Colors.BLACK,
                Colors.WHITE,
            )
            g2d.drawImage(im, 100, y)
            y += im.height + 10
        }
    }

    companion object {
        init {
            if (FactoryProvider.getInstance() == null) {
                FactoryProvider.setInstance(FactoryProviderDesktop())
            }
        }

        @JvmStatic
        fun main(args: Array<String>) {
            Configuration.getFontMapping()
            val s = TestJlmDesktop()
            s.isVisible = true
        }
    }
}
