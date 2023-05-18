package com.mshdabiola.retex.aimplementation.graphics

import androidx.compose.ui.graphics.Color


class ColorA : com.himamis.retex.renderer.share.platform.graphics.Color {
    val color: Color

    constructor(color: Color) {
        this.color = color
    }

    constructor(red: Int, green: Int, blue: Int, alpha: Int) {
        color = Color(red, green, blue, alpha)
    }


    override fun hashCode(): Int {
        return color.hashCode()
    }

    override fun equals(obj: Any?): Boolean {
        if (this === obj) return true
        if (obj == null) return false
        if (javaClass != obj.javaClass) return false
        val other = obj as ColorA
        return color == other.color
    }
}