/*
 *abiola 2024
 */

package com.mshdabiola.seriesmodel

import kotlinx.serialization.Serializable

@Serializable
data class QuestionWithOptsInstTop(
    val question: Question,
    val options: List<Option>,
    val instruction: Instruction?,
    val topic: Topic?,
)
