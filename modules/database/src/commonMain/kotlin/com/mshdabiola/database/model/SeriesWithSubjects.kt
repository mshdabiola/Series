package com.mshdabiola.database.model

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.exam.SubjectEntity

data class SeriesWithSubjects(
    @Embedded val seriesEntity: SeriesEntity,
    @Relation(parentColumn = "id", entityColumn = "seriesId")
    val subjectEntities: List<SubjectEntity>
)
