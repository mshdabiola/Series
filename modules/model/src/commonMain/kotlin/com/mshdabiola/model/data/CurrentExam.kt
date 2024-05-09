package com.mshdabiola.model.data


data class CurrentExam(
    val id: Long,
    val currentTime: Long = 0,
    val totalTime: Long = 4,
    val isSubmit: Boolean = false,
    val paperIndex: Int = 0,
    val examPart: Int = 0,
    val choose: List<List<Int>>,
)
