package com.mshdabiola.database.model.exam

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.SeriesEntity

data class SubjectWithSeries(
    @Embedded val subjectEntity: SubjectEntity,
    @Relation(parentColumn = "seriesId", entityColumn = "id")
    val seriesEntity: SeriesEntity,
)
