package com.mshdabiola.retex.aimplementation

import com.himamis.retex.renderer.share.TeXConstants
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.TeXIcon
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.mshdabiola.retex.aimplementation.graphics.Graphics2DA
import com.mshdabiola.retex.aimplementation.graphics.ShapeUi
import java.io.File

class Formulae(val dpi: Double = 1.0) {


    init {
        FactoryProvider.setInstance(FactoryProviderAndroid(File("")))
        TeXFormula.setDPITarget(dpi)
    }


    fun getShapes(string: String, size: Double = 200.0): List<ShapeUi> {

        return TeXFormula(string).TeXIconBuilder()
            .setStyle(TeXConstants.STYLE_DISPLAY)
            .setSize(size)
            .build()
            .getShapes()
    }

    companion object {
        private var formulae: Formulae? = null

        fun getShapes(dpi: Double, equationStr: String): List<ShapeUi> {
            if (formulae == null) {
                formulae = Formulae(dpi)
            }
            return formulae!!.getShapes(equationStr)
        }

    }

}


fun TeXIcon.getShapes(): List<ShapeUi> {
    val graphics2DA = Graphics2DA()
    paintIcon(null, graphics2DA, 10.0, 10.0)
    return graphics2DA.shape
}