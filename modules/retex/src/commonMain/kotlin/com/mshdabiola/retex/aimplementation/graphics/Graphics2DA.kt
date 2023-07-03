package com.mshdabiola.retex.aimplementation.graphics

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.geom.Line2D
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform
import com.mshdabiola.retex.aimplementation.font.FontA
import com.mshdabiola.retex.aimplementation.font.FontRenderContextA
import com.mshdabiola.retex.aimplementation.geom.Line2DA
import com.mshdabiola.retex.aimplementation.geom.Rectangle2DA
import com.mshdabiola.retex.aimplementation.geom.RoundRectangle2DA

class Graphics2DA : Graphics2DInterface {
    private var mStroke: Stroke = StrokeA()
    private var mColor = ColorA(androidx.compose.ui.graphics.Color.Black)
    private var mTransform: Transform = TransformA()
    val shape = ArrayList<ShapeUi>()
    private val scaleStack = ScaleStack()
    private var pos = Offset.Zero
    private var mFont = FontA("Serif", Font.PLAIN, 10)
    var scale = Offset(1f, 1f)
    override fun setStroke(stroke: Stroke) {
        mStroke = stroke
    }

    override fun getStroke(): Stroke {
        return mStroke
    }

    override fun setColor(color: Color) {
        mColor = color as ColorA
        //set paint color
    }

    override fun getColor(): Color {
        return mColor

    }

    override fun getTransform(): Transform {
        println("get transformation")
        return mTransform
    }

    override fun saveTransformation() {
        println("save transformation")
        scaleStack.pushScaleValues()
    }

    override fun restoreTransformation() {
        println("restore transformation")
        scale = Offset(1f, 1f)
    }

    override fun getFont(): Font {
        println("get font")
        return mFont
    }

    override fun setFont(font: Font) {
        println("set font ${(font as FontA).name}, size${(font as FontA).size} ")
        mFont = font
    }

    fun drawRectangle(rect: Rectangle2DA, isFill: Boolean) {

        val y = rect.topLeft.y
        val topLeft = rect.topLeft.copy(y = (y + (TeXFormula.PIXELS_PER_POINT / 2f)).toFloat())
        shape.add(
            ShapeUi.Rectangle(
                topLeft = topLeft,
                size = rect.size,
                color = mColor.color,
                isFill = isFill
            )
        )
    }

    override fun fillRect(x: Double, y: Double, width: Double, height: Double) {
        println("$x $y $width $height")
//        val rect = Rect(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat())
//        val scale = scaleStack.scaleRectF(rect)
//        val amend = AmendRect.amendRectF(scale)
        drawRectangle(Rectangle2DA(x, y, width, height), true)


    }

    override fun fill(rectangle: Shape) {
        println("fill rectangle")
        draw(rectangle as Rectangle2D)
    }

    override fun startDrawing() {
        println("Start drawing")
    }

    override fun moveTo(x: Double, y: Double) {
        println("move to $x $y")
    }

    override fun lineTo(x: Double, y: Double) {
        println("line to $x $y")
        TODO("line")
    }

    override fun quadraticCurveTo(x: Double, y: Double, x1: Double, y1: Double) {
        println("quadratic curve to $x $y $x1 $y1")
        TODO("quadratic curve to")
    }

    override fun bezierCurveTo(
        x: Double,
        y: Double,
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double
    ) {
        TODO("bezier curve to")
    }

    override fun finishDrawing() {
        println("finish drawing")
    }

    override fun draw(rectangle: Rectangle2D) {
        val rect = rectangle as Rectangle2DA
        drawRectangle(rect, true)
        //fillRect(rectangle.x,rectangle.y,rectangle.width,rectangle.height)
    }

    override fun draw(rectangle: RoundRectangle2D) {
        println("draw ${(rectangle as RoundRectangle2DA)}")
    }

    override fun draw(line: Line2D?) {
        println("draw")
        val impl = line as Line2DA
        shape.add(
            ShapeUi.Line(
                Offset(impl.xx1.toFloat(), impl.yy1.toFloat()),
                Offset(impl.xx1.toFloat(), impl.yy1.toFloat())
            )
        )
    }

    override fun drawChars(data: CharArray, offset: Int, length: Int, x: Int, y: Int) {
        println("draw char ${data.joinToString()} offset$offset length $length x $x y $y")
        val size = Size(
            (TeXFormula.PIXELS_PER_POINT * scale.x).toFloat(),
            (TeXFormula.PIXELS_PER_POINT * scale.y).toFloat()
        )
        shape.add(ShapeUi.Text(pos, scale.x, data.joinToString(), mFont.name))
    }

    override fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        println("draw arc")
    }

    override fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        println("fill arc")
    }

    override fun translate(x: Double, y: Double) {
        println("translate $x $y")
        pos = Offset(x.toFloat(), y.toFloat())
    }

    override fun scale(x: Double, y: Double) {
        println("scale $x $y")
        scale = Offset(x.toFloat(), y.toFloat())
    }

    override fun rotate(theta: Double, x: Double, y: Double) {
        println("rotate")
    }

    override fun rotate(theta: Double) {
        println("rotate")
    }

    override fun drawImage(image: Image?, x: Int, y: Int) {
        println("draw image")
    }

    override fun drawImage(image: Image?, transform: Transform?) {
        println("draw image")
    }

    override fun getFontRenderContext(): FontRenderContext {
        return FontRenderContextA()
    }

    override fun setRenderingHint(key: Int, value: Int) {
        println("set rendering hint")
    }

    override fun getRenderingHint(key: Int): Int {
        println("get rendering hint")
        return 1
    }

    override fun dispose() {
        println("dispose")
    }

    fun drawString(mString: String, x: Int, y: Int) {
        println("draw string $mString")
    }
}

// rectangle
// round rectangle
// line
// text
// draw Arc
// fill arc
// move
// draw image

sealed interface ShapeUi {
    val topLeft: Offset

    data class Rectangle(
        override val topLeft: Offset,
        val size: Size,
        val isFill: Boolean = false,
        val color: androidx.compose.ui.graphics.Color = androidx.compose.ui.graphics.Color.Transparent
    ) : ShapeUi

    data class Line(override val topLeft: Offset, val bottomRight: Offset) : ShapeUi

    data class Text(
        override val topLeft: Offset,
        val scale: Float,
        val text: String,
        val fontPath: String
    ) : ShapeUi
}