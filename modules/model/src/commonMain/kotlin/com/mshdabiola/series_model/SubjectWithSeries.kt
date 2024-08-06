package com.mshdabiola.series_model

import kotlinx.serialization.Serializable

@Serializable
data class SubjectWithSeries(
    val subject: Subject,
    val series: Series,
)
