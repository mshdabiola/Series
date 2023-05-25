package com.mshdabiola.ui.state

import com.mshdabiola.model.data.Type

data class ItemUi(
    val content: String = "",
    val type: Type = Type.TEXT,
    val isEditMode: Boolean = false
)
