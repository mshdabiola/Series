/*
 *abiola 2024
 */

package com.mshdabiola.database.model

import androidx.room.Embedded
import androidx.room.Relation

data class ExaminationFull(
    @Embedded val examinationEntity: ExaminationEntity,
    @Relation(parentColumn = "subjectId", entityColumn = "id")
    val subjectEntity: SubjectEntity,

)
