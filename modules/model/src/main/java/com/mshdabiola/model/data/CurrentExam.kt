package com.mshdabiola.model.data

import kotlinx.serialization.Serializable

@Serializable
data class CurrentExam(
    val id: Long,
    val currentTime: Long = 0,
    val totalTime: Long = 4,
    val isSubmit: Boolean = false,
    val examPart: Int = 0,
    val chooseObj: List<Int>,
    val chooseThe: List<Int>,
)


