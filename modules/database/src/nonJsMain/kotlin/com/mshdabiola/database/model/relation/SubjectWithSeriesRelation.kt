package com.mshdabiola.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.SeriesEntity
import com.mshdabiola.database.model.SubjectEntity

data class SubjectWithSeriesRelation(
    @Embedded val subjectEntity: SubjectEntity,
    @Relation(parentColumn = "seriesId", entityColumn = "id")
    val seriesEntity: SeriesEntity,
)
