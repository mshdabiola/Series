package com.mshdabiola.model

sealed interface Item{
    data class Text(val tex: String):Item
    data class Image(val imageName:String):Item

    data class Equation(val tex: String):Item
}

