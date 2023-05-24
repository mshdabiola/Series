package com.mshdabiola.series.feature.exam.state
sealed interface ItemUi{
    val isEditMode : Boolean
    data class Text(val tex: String, override val isEditMode: Boolean=false):ItemUi
    data class Image(val imageName:String, override val isEditMode: Boolean=false):ItemUi

    data class Equation(val tex: String, override val isEditMode: Boolean=false):ItemUi


}