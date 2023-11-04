package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Subject
import kotlinx.coroutines.flow.Flow

interface ISubjectRepository {


    fun getAll(): Flow<List<Subject>>
}