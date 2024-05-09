package com.mshdabiola.datastore

import com.mshdabiola.datastore.model.CurrentExamSer
import com.mshdabiola.datastore.model.InstructionSer
import com.mshdabiola.datastore.model.QuestionSer
import com.mshdabiola.datastore.model.UserDataSer
import kotlinx.coroutines.flow.Flow

interface Store {

    val userData: Flow<UserDataSer>
    val questions: Flow<Map<Long, QuestionSer>>
    val instructions: Flow<Map<Long, InstructionSer>>
    val currentExam: Flow<CurrentExamSer>


    suspend fun updateUserData(transform: suspend (UserDataSer) -> UserDataSer): UserDataSer

    suspend fun updateQuestion(transform: suspend (Map<Long, QuestionSer>) -> Map<Long, QuestionSer>): Map<Long, QuestionSer>
    suspend fun updateInstruction(transform: suspend (Map<Long, InstructionSer>) -> Map<Long, InstructionSer>): Map<Long, InstructionSer>
    suspend fun updateCurrentQuestion(transform: suspend (CurrentExamSer) -> CurrentExamSer): CurrentExamSer

}
