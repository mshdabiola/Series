package com.mshdabiola.seriesdatabase.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.seriesdatabase.model.SeriesEntity
import com.mshdabiola.seriesdatabase.model.SubjectEntity

data class SubjectWithSeriesRelation(
    @Embedded val subjectEntity: SubjectEntity,
    @Relation(parentColumn = "seriesId", entityColumn = "id")
    val seriesEntity: SeriesEntity,
)
