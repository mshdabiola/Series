package com.himamis.retex.renderer.desktop.box

import com.himamis.retex.renderer.share.Box
import com.himamis.retex.renderer.share.ShapeBox
import com.himamis.retex.renderer.share.platform.box.BoxDecorator

/**
 * Creates a ShapeBox.
 */
class ShapeBoxDecorator : BoxDecorator {
    override fun decorate(box: Box): Box {
        return ShapeBox.create(box)
    }
}
