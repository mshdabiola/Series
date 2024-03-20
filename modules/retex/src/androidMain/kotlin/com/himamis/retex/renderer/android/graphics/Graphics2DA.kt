package com.himamis.retex.renderer.android.graphics

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.DashPathEffect
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PathEffect
import android.graphics.RectF
import android.view.View
import androidx.compose.ui.graphics.asAndroidBitmap
import com.himamis.retex.renderer.android.font.FontA
import com.himamis.retex.renderer.android.font.FontRenderContextA
import com.himamis.retex.renderer.android.geom.Line2DA
import com.himamis.retex.renderer.android.geom.Rectangle2DA
import com.himamis.retex.renderer.android.geom.RoundRectangle2DA
import com.himamis.retex.renderer.share.ColorUtil
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext
import com.himamis.retex.renderer.share.platform.geom.Line2D
import com.himamis.retex.renderer.share.platform.geom.Rectangle2D
import com.himamis.retex.renderer.share.platform.geom.RoundRectangle2D
import com.himamis.retex.renderer.share.platform.geom.Shape
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.Graphics2DInterface
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.RenderingHints
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform

@SuppressLint("NewApi")
class Graphics2DA() : Graphics2DInterface {
    private var mCanvas: Canvas? = null
    private var mView: View? = null
    private val mDrawPaint: Paint
    private val mFontPaint: Paint

    // The canvas is never scaled directly because with hardwareAcceleration:on
    // the drawing be pixelated. Instead the scale values are saved in a stack
    private val mScaleStack: ScaleStack
    private var mFont: FontA
    private var mColor: ColorA
    private var mOldDrawPaintStyle: Paint.Style? = null
    private var mStroke: Stroke? = null

    init {
        mDrawPaint = Paint()
        mDrawPaint.style = Paint.Style.STROKE
        mDrawPaint.isSubpixelText = true
        mDrawPaint.isAntiAlias = true
        mDrawPaint.isLinearText = true
        mFontPaint = Paint()
        mFontPaint.set(mDrawPaint)
        mScaleStack = ScaleStack()
        mFont = FontA("Serif", Font.PLAIN, 10)
        mColor = ColorUtil.BLACK as ColorA
    }

    constructor(canvas: Canvas?) : this() {
        setCanvas(canvas)
    }

    constructor(canvas: Canvas?, view: View?) : this(canvas) {
        setView(view)
    }

    fun setCanvas(canvas: Canvas?) {
        mCanvas = canvas
    }

    fun setView(view: View?) {
        mView = view
    }

    private fun setBasicStroke(basicStroke: BasicStrokeA) {
        val strokeWidth = mScaleStack.scaleThickness(basicStroke.width.toFloat())
        mDrawPaint.strokeWidth = strokeWidth
        mDrawPaint.strokeMiter = basicStroke.miterLimit.toFloat()
        mDrawPaint.strokeCap = basicStroke.nativeCap
        mDrawPaint.strokeJoin = basicStroke.nativeJoin
        var pathEffect: PathEffect? = null
        if (basicStroke.dashes != null) {
            val dashes = convertDashes(basicStroke.dashes)
            pathEffect = DashPathEffect(dashes, 0.0f)
        }
        mDrawPaint.setPathEffect(pathEffect)
    }

    private fun convertDashes(dashes: DoubleArray?): FloatArray {
        val scaledDashes = FloatArray(dashes!!.size)
        for (i in dashes.indices) {
            scaledDashes[i] = mScaleStack.scaleThickness(dashes[i].toFloat())
        }
        return scaledDashes
    }

    override fun getStroke(): Stroke? {
        return if (mStroke != null) {
            mStroke
        } else {
            BasicStrokeA(
                mDrawPaint.strokeWidth.toDouble(),
                mDrawPaint.strokeMiter.toDouble(),
                mDrawPaint.strokeCap,
                mDrawPaint.strokeJoin,
            )
        }
    }

    override fun setStroke(stroke: Stroke) {
        mStroke = stroke
        setBasicStroke(stroke as BasicStrokeA)
    }

    override fun getColor(): Color {
        return mColor
    }

    override fun setColor(color: Color) {
        mColor = color as ColorA
        mDrawPaint.color = mColor.color
    }

    @Suppress("deprecation")
    override fun getTransform(): Transform {
        var matrix: Matrix? = null
        if (mView != null) {
            matrix = mView!!.matrix
        }
        if (matrix == null) {
            matrix = mCanvas!!.matrix
        }
        val transform = TransformA(matrix)
        transform.scale(mScaleStack.scaleX.toDouble(), mScaleStack.scaleY.toDouble())
        return transform
    }

    override fun getFont(): Font {
        return mFont
    }

    override fun setFont(font: Font) {
        mFont = font as FontA
        mDrawPaint.setTypeface(mFont.typeface)
        mDrawPaint.textSize = mScaleStack.scaleFontSize(mFont.size.toFloat())
    }

    override fun fillRect(x: Double, y: Double, width: Double, height: Double) {
        beforeFill()
        val rectF = RectF(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat())
        val scaled = mScaleStack.scaleRectF(rectF)
        val amended = AmendRect.amendRectF(scaled)
        mCanvas!!.drawRect(amended!!, mDrawPaint)
        afterFill()
    }

    override fun fill(rectangle: Shape) {
        if (rectangle is Rectangle2D) {
            beforeFill()
            draw(rectangle)
            afterFill()
        }
    }

    override fun draw(rectangle: Rectangle2D) {
        val rect = (rectangle as Rectangle2DA).rectF
        val copy = RectF(rect)
        val scaled = mScaleStack.scaleRectF(copy)
        val amended = AmendRect.amendRectF(scaled)
        mCanvas!!.drawRect(amended!!, mDrawPaint)
    }

    override fun draw(rectangle: RoundRectangle2D) {
        val rect = (rectangle as RoundRectangle2DA).rectF
        val copy = RectF(rect)
        mCanvas!!.drawRoundRect(
            mScaleStack.scaleRectF(copy),
            mScaleStack.scaleX(rectangle.getArcW().toFloat()),
            mScaleStack.scaleY(rectangle.getArcH().toFloat()),
            mDrawPaint,
        )
    }

    override fun draw(line: Line2D) {
        val impl = line as Line2DA
        val start = impl.startPoint
        val end = impl.endPoint
        mCanvas!!.drawLine(
            mScaleStack.scaleX(start!!.x),
            mScaleStack.scaleY(start.y),
            mScaleStack.scaleX(
                end!!.x,
            ),
            mScaleStack.scaleY(end.y),
            mDrawPaint,
        )
    }

    override fun drawChars(data: CharArray, offset: Int, length: Int, x: Int, y: Int) {
        beforeFill()
        mDrawPaint.textSize = mScaleStack.scaleFontSize(mFont.size.toFloat())
        mCanvas!!.drawText(
            data,
            offset,
            length,
            mScaleStack.scaleX(x.toFloat()),
            mScaleStack.scaleY(y.toFloat()),
            mDrawPaint,
        )
        afterFill()
    }

    fun drawString(text: String?, x: Int, y: Int, paint: Paint?) {
        paint!!.textSize = mScaleStack.scaleFontSize(paint.textSize)
        paint.color = mDrawPaint.color
        mCanvas!!.drawText(
            text!!,
            mScaleStack.scaleX(x.toFloat()),
            mScaleStack.scaleY(y.toFloat()),
            paint,
        )
    }

    override fun drawArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        val oval = RectF(x.toFloat(), y.toFloat(), (x + width).toFloat(), (y + height).toFloat())
        mCanvas!!.drawArc(
            mScaleStack.scaleRectF(oval),
            startAngle.toFloat(),
            arcAngle.toFloat(),
            false,
            mDrawPaint,
        )
    }

    override fun fillArc(x: Int, y: Int, width: Int, height: Int, startAngle: Int, arcAngle: Int) {
        beforeFill()
        drawArc(x, y, width, height, startAngle, arcAngle)
        afterFill()
    }

    override fun translate(x: Double, y: Double) {
        mCanvas!!.translate(mScaleStack.scaleX(x.toFloat()), mScaleStack.scaleY(y.toFloat()))
    }

    override fun scale(x: Double, y: Double) {
        mScaleStack.appendScale(x.toFloat(), y.toFloat())
    }

    override fun rotate(theta: Double, x: Double, y: Double) {
        translate(x, y)
        rotate(theta)
        translate(-x, -y)
    }

    override fun rotate(theta: Double) {
        // theta is in radians
        // change to degrees
        val degrees = Math.toDegrees(theta).toFloat()
        mCanvas!!.rotate(degrees)
    }

    override fun drawImage(image: Image, x: Int, y: Int) {
        val imageA = image as ImageA
        val bitmap = imageA.bitmap
        mCanvas!!.drawBitmap(
            mScaleStack.scaleBitmap(bitmap.asAndroidBitmap()),
            mScaleStack.scaleX(x.toFloat()),
            mScaleStack.scaleY(y.toFloat()),
            mDrawPaint,
        )
    }

    override fun drawImage(image: Image, transform: Transform) {
        val imageA = image as ImageA
        val bitmap = imageA.bitmap
        mCanvas!!.drawBitmap(
            mScaleStack.scaleBitmap(bitmap.asAndroidBitmap()),
            (transform as Matrix),
            mDrawPaint,
        )
    }

    override fun getFontRenderContext(): FontRenderContext {
        mFontPaint.set(mDrawPaint)
        return FontRenderContextA(mFontPaint)
    }

    override fun setRenderingHint(key: Int, value: Int) {
        if (key == RenderingHints.KEY_ANTIALIASING && value == RenderingHints.VALUE_ANTIALIAS_ON) {
            mDrawPaint.isAntiAlias = true
        } else {
            // No other rendering hint is supported
        }
    }

    override fun getRenderingHint(key: Int): Int {
        // Not supported
        return -1
    }

    override fun dispose() {
        // NO-OP
    }

    private fun saveDrawPaintStyle() {
        mOldDrawPaintStyle = mDrawPaint.style
    }

    private fun restoreDrawPaintStyle() {
        mDrawPaint.style = mOldDrawPaintStyle
    }

    private fun setDrawPaintFillStyle() {
        mDrawPaint.style = Paint.Style.FILL
    }

    private fun beforeFill() {
        saveDrawPaintStyle()
        setDrawPaintFillStyle()
    }

    private fun afterFill() {
        restoreDrawPaintStyle()
    }

    override fun saveTransformation() {
        mCanvas!!.save()
        mScaleStack.pushScaleValues()
    }

    override fun restoreTransformation() {
        mCanvas!!.restore()
        mScaleStack.popScaleValues()
    }

    override fun startDrawing() {}
    override fun moveTo(x: Double, y: Double) {}
    override fun lineTo(x: Double, y: Double) {}
    override fun quadraticCurveTo(x: Double, y: Double, x1: Double, y1: Double) {}
    override fun bezierCurveTo(
        x: Double,
        y: Double,
        x1: Double,
        y1: Double,
        x2: Double,
        y2: Double,
    ) {
    }

    override fun finishDrawing() {}
}
