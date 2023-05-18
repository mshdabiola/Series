package com.mshdabiola.retex.aimplementation.font

import com.himamis.retex.renderer.share.platform.font.Font
import com.himamis.retex.renderer.share.platform.font.FontRenderContext

class FontRenderContextA() : FontRenderContext {

    override fun getFont(): Font? {
        // not used, web only
        return null
    }
}