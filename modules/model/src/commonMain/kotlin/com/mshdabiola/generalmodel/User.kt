package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val id: Long=-1,
    val name: String,
    val type: UserType,
    val password: String,
    val imagePath: String,
    val points :Long
)
