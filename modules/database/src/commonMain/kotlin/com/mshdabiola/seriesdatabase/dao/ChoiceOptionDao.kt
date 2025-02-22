package com.mshdabiola.seriesdatabase.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.mshdabiola.seriesdatabase.model.ChoiceOptionEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ChoiceOptionDao {
    @Upsert
    suspend fun upsert(choiceOption: ChoiceOptionEntity): Long

    @Upsert
    suspend fun upsertAll(choiceOptions: List<ChoiceOptionEntity>)

    @Delete
    suspend fun delete(choiceOption: ChoiceOptionEntity)

    @Query("DELETE FROM choice_options")
    suspend fun deleteAllChoiceOptions()

    @Query("SELECT * FROM choice_options WHERE option_id = :optionId")
    fun getChoiceOptionById(optionId: Int): Flow<ChoiceOptionEntity?>

    @Query("SELECT * FROM choice_options")
    fun getAllChoiceOptions(): Flow<List<ChoiceOptionEntity>>

    @Query("SELECT * FROM choice_options WHERE exam_question_id_fk = :questionId")
    fun getChoiceOptionsByQuestionId(questionId: Int): Flow<List<ChoiceOptionEntity>>
}
