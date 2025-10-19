/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.mshdabiola.database.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    foreignKeys = [
        ForeignKey(
            entity = PaperEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("paperId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["paperId"])],

    tableName = "session_question",
)
data class SessionQuestion(
    @PrimaryKey(true)
    val id: Long?,
    val paperId: Long,
    val questionId: Long,
    val chosenOptionId: Long,
    val answer: String,
    val isCorrect: Boolean,
)
