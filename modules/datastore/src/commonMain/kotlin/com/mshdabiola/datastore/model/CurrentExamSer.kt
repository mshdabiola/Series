package com.mshdabiola.datastore.model

import kotlinx.serialization.Serializable

@Serializable

data class CurrentExamSer(
    val id: Long,
    val currentTime: Long = 0,
    val totalTime: Long = 4,
    val isSubmit: Boolean = false,
    val paperIndex: Int = 0,
    val examPart: Int = 0,
    val choose: List<List<Int>>,
)
