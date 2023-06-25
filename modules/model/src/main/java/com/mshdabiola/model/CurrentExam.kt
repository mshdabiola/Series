package com.mshdabiola.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentExam(
    val id :Long,
    val choose : List<Pair<Int,Int>>
)


