package com.himamis.retex.renderer.android.font

import android.graphics.Paint
import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext

class FontRenderContextA(val paint: Paint) : FontRenderContext {

    override fun getFont(): Font? {
        // not used, web only
        return null
    }
}
