package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class SubjectWithSeries(
    val subject: Subject,
    val series: Series,
)
