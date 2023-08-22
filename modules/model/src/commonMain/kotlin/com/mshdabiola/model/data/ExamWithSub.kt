package com.mshdabiola.model.data

data class ExamWithSub(
    val id: Long = -1,
    val subjectID: Long,
    val year: Long,
    val isObjOnly: Boolean,
    val subject: String,
    val examTime: Long
)
