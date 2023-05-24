package com.mshdabiola.model.data

data class Item(val content:String,val type:Type=Type.TEXT)

enum class Type{
    TEXT,
    IMAGE,
    EQUATION
}
