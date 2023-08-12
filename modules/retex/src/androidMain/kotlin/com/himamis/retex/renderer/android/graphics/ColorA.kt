package com.himamis.retex.renderer.android.graphics

import com.himamis.retex.renderer.share.platform.graphics.Color

class ColorA : Color {
    var color: Int
        private set

    constructor(color: Int) {
        this.color = color
    }

    constructor(red: Int, green: Int, blue: Int, alpha: Int) {
        color = android.graphics.Color.argb(alpha, red, green, blue)
    }

    val nativeObject: Any
        get() = Integer.valueOf(color)

    override fun hashCode(): Int {
        return color
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ColorA
        return if (color != other.color) false else true
    }
}

