/*
 *abiola 2024
 */

package com.mshdabiola.database.model.exam

import androidx.room.Embedded
import androidx.room.Relation

data class ExaminationWithSubject(
    @Embedded val examinationEntity: ExaminationEntity,
    @Relation(entity = SubjectEntity::class, parentColumn = "subjectId", entityColumn = "id")
    val subjectWithSeries: SubjectWithSeries,
    )
