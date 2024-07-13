/*
 *abiola 2024
 */

package com.mshdabiola.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.ExaminationEntity
import com.mshdabiola.database.model.SubjectEntity

data class ExaminationWithSubject(
    @Embedded val examinationEntity: ExaminationEntity,
    @Relation(entity = SubjectEntity::class, parentColumn = "subjectId", entityColumn = "id")
    val subjectWithSeries: SubjectWithSeries,
)
