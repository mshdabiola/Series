package com.mshdabiola.model.data

data class Examination(
    val id: Long?,
    val year: Long,
    val isObjectiveOnly: Boolean,
    val duration: Long,
    val updateTime: Long,
    val subject: Subject
)
