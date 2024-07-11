/*
 *abiola 2024
 */

package com.mshdabiola.generalmodel

import kotlinx.serialization.Serializable

@Serializable
data class ExaminationWithSubject(
    val examination: Examination,
    val subject: Subject,
    val series: Series
)
