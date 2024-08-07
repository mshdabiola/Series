/*
 *abiola 2024
 */

package com.mshdabiola.seriesdatabase.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.seriesdatabase.model.ExaminationEntity
import com.mshdabiola.seriesdatabase.model.SubjectEntity

data class ExaminationWithSubjectRelation(
    @Embedded val examinationEntity: ExaminationEntity,
    @Relation(entity = SubjectEntity::class, parentColumn = "subjectId", entityColumn = "id")
    val subjectWithSeries: SubjectWithSeriesRelation,
)
