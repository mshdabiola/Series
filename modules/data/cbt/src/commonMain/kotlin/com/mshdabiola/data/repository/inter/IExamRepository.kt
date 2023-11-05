package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.ExamWithSub
import kotlinx.coroutines.flow.Flow

interface IExamRepository {

    fun getAllWithSub(): Flow<List<ExamWithSub>>

}