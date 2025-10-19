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
package com.mshdabiola.database.model.relation

import androidx.room.Embedded
import androidx.room.Relation
import com.mshdabiola.database.model.InstructionEntity
import com.mshdabiola.database.model.OptionEntity
import com.mshdabiola.database.model.QuestionEntity
import com.mshdabiola.database.model.TopicEntity

data class QuestionWithOptsInstTopRelation(
    @Embedded val questionEntity: QuestionEntity,
    @Relation(parentColumn = "id", entityColumn = "questionId")
    val options: List<OptionEntity>,
    @Relation(parentColumn = "instructionId", entityColumn = "id")
    val instructionEntity: InstructionEntity?,
    @Relation(entity = TopicEntity::class, parentColumn = "topicId", entityColumn = "id")
    val topicWithCategoryRelation: TopicWithCategoryRelation?,
)
