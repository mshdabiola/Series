/*
 *abiola 2024
 */

package com.mshdabiola.series_database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.series_database.model.ExaminationEntity
import com.mshdabiola.series_database.model.SubjectEntity

data class ExaminationWithSubjectRelation(
    @Embedded val examinationEntity: ExaminationEntity,
    @Relation(entity = SubjectEntity::class, parentColumn = "subjectId", entityColumn = "id")
    val subjectWithSeries: SubjectWithSeriesRelation,
)
