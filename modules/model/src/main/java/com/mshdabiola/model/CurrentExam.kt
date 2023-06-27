package com.mshdabiola.model

import kotlinx.serialization.Serializable

@Serializable
data class CurrentExam(
    val id :Long,
    val currentTime:Long=0,
    val totalTime : Long=4,
    val isSubmit:Boolean=false,
    val progress:Float=0.0f,
    val choose : List<Pair<Int,Int>>
)


