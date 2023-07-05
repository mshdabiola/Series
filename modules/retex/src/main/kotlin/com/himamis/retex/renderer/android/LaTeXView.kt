package com.himamis.retex.renderer.android

import android.content.Context
import android.content.res.Configuration
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import com.himamis.retex.renderer.android.graphics.ColorA
import com.himamis.retex.renderer.android.graphics.Graphics2DA
import com.himamis.retex.renderer.share.TeXConstants
import com.himamis.retex.renderer.share.TeXFormula
import com.himamis.retex.renderer.share.TeXFormula.TeXIconBuilder
import com.himamis.retex.renderer.share.TeXIcon
import com.himamis.retex.renderer.share.exception.ParseException
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.Insets
import com.mshdabiola.retex.R
import java.lang.ref.WeakReference
import java.util.concurrent.Executors
import java.util.concurrent.Future

class LaTeXView : View {
    private val mServicePool = Executors.newSingleThreadExecutor()
    private var mTexIconBuilderFuture: Future<*>? = null

    @Volatile
    private var mFormula: TeXFormula? = null

    @Volatile
    private var mTexIconBuilder: TeXIconBuilder? = null

    @Volatile
    private var mTexIcon: TeXIcon? = null
    private var mGraphics: Graphics2DA? = null

    @Volatile
    private var mLatexText: String? = ""

    @Volatile
    private var mSize = 20f

    @Volatile
    private var mStyle = TeXConstants.STYLE_DISPLAY

    @Volatile
    private var mType = TeXFormula.SERIF
    private var mBackgroundColor = android.graphics.Color.TRANSPARENT
    private var mForegroundColor: Color = ColorA(android.graphics.Color.BLACK)
    private var mScreenDensity: Float
    private var mSizeScale: Float
    private val mTexIconBuilderRunnable = TexIconCreatorRunnable()
    private val mCleanFormula = Runnable {
        mFormula = null
        mTexIconBuilder = null
        mTexIcon = null
    }
    private val mCleanTexIcon = Runnable { mTexIcon = null }
    private val mRequestLayout: Runnable = RequestLayoutAction(this)

    constructor(context: Context) : super(context) {
        mScreenDensity = context.resources.displayMetrics.density
        mSizeScale = context.resources.displayMetrics.scaledDensity
        initFactoryProvider()
        ensureTeXIconExists()
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        mScreenDensity = context.resources.displayMetrics.density
        mSizeScale = context.resources.displayMetrics.scaledDensity
        initFactoryProvider()
        readAttributes(context, attrs, 0)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        mScreenDensity = context.resources.displayMetrics.density
        mSizeScale = context.resources.displayMetrics.scaledDensity
        initFactoryProvider()
        readAttributes(context, attrs, defStyleAttr)
    }

    private fun initFactoryProvider() {
        if (FactoryProvider.getInstance() == null) {
            FactoryProvider.setInstance(
                FactoryProviderAndroid(
                    context.assets
                )
            )
        }
    }

    private fun readAttributes(context: Context, attrs: AttributeSet?, defStyleAttr: Int) {
        val a = context.theme.obtainStyledAttributes(
            attrs,
            R.styleable.LaTeXView,
            defStyleAttr, 0
        )
        try {
            mLatexText = a.getString(R.styleable.LaTeXView_lv_latexText)
            mSize = a.getFloat(R.styleable.LaTeXView_lv_size, 20f)
            mStyle = a.getInteger(R.styleable.LaTeXView_lv_style, 0)
            mType = a.getInteger(R.styleable.LaTeXView_lv_type, TeXFormula.SERIF)
            mBackgroundColor = a.getColor(
                R.styleable.LaTeXView_lv_backgroundColor,
                android.graphics.Color.TRANSPARENT
            )
            mForegroundColor = ColorA(
                a.getColor(
                    R.styleable.LaTeXView_lv_foregroundColor,
                    android.graphics.Color.BLACK
                )
            )
        } finally {
            a.recycle()
        }
        ensureTeXIconExists()
    }

    /**
     * Sets the LaTeX text of this view. Must be called from the UI thread.
     *
     * @param latexText formula in LaTeX format
     */
    fun setLatexText(latexText: String?) {
        mLatexText = latexText
        cleanFormula()
        ensureTeXIconExists()
        invalidate()
        requestLayout()
    }

    /**
     * Sets the size of the text. Must be called from the UI thread.
     *
     * @param size size
     */
    fun setSize(size: Float) {
        if (Math.abs(mSize - size) > 0.01) {
            mSize = size
            cleanTexIcon()
            ensureTeXIconExists()
            invalidate()
            requestLayout()
        }
    }

    /**
     * Sets the style of the LaTeX. Must be called from the UI thread.
     *
     * @param style one of [TeXConstants.STYLE_TEXT] , [TeXConstants.STYLE_DISPLAY],
     * [TeXConstants.STYLE_SCRIPT] or [TeXConstants.STYLE_SCRIPT_SCRIPT]
     */
    fun setStyle(style: Int) {
        if (mStyle != style) {
            mStyle = style
            cleanTexIcon()
            ensureTeXIconExists()
            invalidate()
            requestLayout()
        }
    }

    /**
     * Sets the type of the LaTeX view. Must be called from the UI thread.
     *
     * @param type one of [TeXFormula.SERIF], [TeXFormula.SANSSERIF],
     * [TeXFormula.BOLD], [TeXFormula.ITALIC], [TeXFormula.ROMAN]
     */
    fun setType(type: Int) {
        if (mType != type) {
            mType = type
            cleanTexIcon()
            ensureTeXIconExists()
            invalidate()
            requestLayout()
        }
    }

    /**
     * Sets the color of the text. Must be called from the UI thread.
     *
     * @param foregroundColor color represented as packed ints
     */
    fun setForegroundColor(foregroundColor: Int) {
        mForegroundColor = ColorA(foregroundColor)
        invalidate()
    }

    /**
     * Sets the color of the background. Must be called from the UI thread.
     *
     * @param backgroundColor color represented as packed ints
     */
    override fun setBackgroundColor(backgroundColor: Int) {
        mBackgroundColor = backgroundColor
        invalidate()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        val newSizeScale = mScreenDensity * newConfig.fontScale
        if (Math.abs(mSizeScale - newSizeScale) > 0.001) {
            mSizeScale = newSizeScale
            cleanTexIcon()
            ensureTeXIconExists()
            invalidate()
            requestLayout()
        }
    }

    private fun ensureTeXIconExists() {
        createTexIcon()
    }

    private val iconWidth: Int
        private get() {
            val teXIcon = mTexIcon ?: return 0
            return teXIcon.iconWidth
        }
    private val iconHeight: Int
        private get() {
            val teXIcon = mTexIcon ?: return 0
            return teXIcon.iconHeight
        }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val desiredWidth = iconWidth
        val desiredHeight = iconHeight
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val width: Int
        val height: Int
        width = if (widthMode == MeasureSpec.EXACTLY) {
            widthSize
        } else if (widthMode == MeasureSpec.AT_MOST) {
            Math.min(desiredWidth, widthSize)
        } else {
            desiredWidth
        }
        height = if (heightMode == MeasureSpec.EXACTLY) {
            heightSize
        } else if (heightMode == MeasureSpec.AT_MOST) {
            Math.min(desiredHeight, heightSize)
        } else {
            desiredHeight
        }
        setMeasuredDimension(width, height)
    }

    override fun onDraw(canvas: Canvas) {
        val teXIcon = mTexIcon ?: return
        if (mGraphics == null) {
            mGraphics = Graphics2DA()
        }
        // draw background
        canvas.drawColor(mBackgroundColor)

        // draw latex
        mGraphics!!.setCanvas(canvas)
        teXIcon.setForeground(mForegroundColor)

        teXIcon.paintIcon(null, mGraphics, 0.0, 0.0)
    }

    internal inner class TexIconCreatorRunnable : Runnable {
        override fun run() {

            if (mFormula == null) {
                mFormula = try {
                    TeXFormula(mLatexText)
                } catch (exception: ParseException) {
                    TeXFormula.getPartialTeXFormula(mLatexText)
                }
            }
            if (mTexIconBuilder == null) {
                mTexIconBuilder = mFormula!!.TeXIconBuilder()
            }
            if (mTexIcon == null) {
                mTexIconBuilder!!.setSize((mSize * mSizeScale).toDouble()).setStyle(mStyle)
                    .setType(mType)
                mTexIcon = mTexIconBuilder!!.build()

            }
            mTexIcon!!.insets = Insets(
                paddingTop,
                paddingLeft,
                paddingBottom,
                paddingRight
            )
            invalidateView()
            requestViewLayout()
        }
    }

    private fun invalidateView() {
        postInvalidate()
    }

    private fun requestViewLayout() {
        post(mRequestLayout)
    }

    /**
     * Static Runnable for wrapping the View.requestLayout() action by using a WeakReference to the
     * LaTeXView.
     * Solution to memory leak issues occurring on devices running on API < 24
     */
    private class RequestLayoutAction internal constructor(view: View) : Runnable {
        private val latexView: WeakReference<View>

        init {
            latexView = WeakReference(view)
        }

        override fun run() {
            val view = latexView.get()
            view?.requestLayout()
        }
    }

    private fun cleanFormula() {
        mServicePool.submit(mCleanFormula)
    }

    private fun cleanTexIcon() {
        mServicePool.submit(mCleanTexIcon)
    }

    private fun createTexIcon() {
        cancelFuture()
        mTexIconBuilderFuture = mServicePool.submit(mTexIconBuilderRunnable)
    }

    private fun cancelFuture() {
        if (mTexIconBuilderFuture != null) {
            mTexIconBuilderFuture!!.cancel(true)
            mTexIconBuilderFuture = null
        }
    }
}
