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
            entity = UserEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("examinerId"),
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["examinerId"])],

    tableName = "session_examination",
)
data class SessionExamination(
    @PrimaryKey(true)
    val id: Long?,
    val examinerId: Long,
    val userId: Long?,
    val generatedFromId: Long?,
    val duration: Long,
    val remainingTime: Long,
    val totalMarks: Long,
    val isCompleted: Boolean,
    val progress: Long,
    val isUploaded: Boolean,
    val showResultImmediately: Boolean,
)
