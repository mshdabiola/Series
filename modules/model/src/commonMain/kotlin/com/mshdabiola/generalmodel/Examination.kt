package com.mshdabiola.generalmodel

data class Examination(
    val id: Long?,
    val year: Long,
    val isObjectiveOnly: Boolean,
    val duration: Long,
    val updateTime: Long,
    val subject: Subject,
)
