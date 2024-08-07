package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class SubjectWithSeries(
    val subject: Subject,
    val series: Series,
)
