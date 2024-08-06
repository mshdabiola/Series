package com.mshdabiola.series_database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.series_database.model.SeriesEntity
import com.mshdabiola.series_database.model.SubjectEntity

data class SubjectWithSeriesRelation(
    @Embedded val subjectEntity: SubjectEntity,
    @Relation(parentColumn = "seriesId", entityColumn = "id")
    val seriesEntity: SeriesEntity,
)
