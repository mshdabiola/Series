package com.mshdabiola.data.repository


import com.mshdabiola.data.repository.inter.IModelRepository
import com.mshdabiola.model.Model
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

internal class RealModelRepository constructor(

) : IModelRepository {
    override suspend fun insert(model: Model) {

    }

    override fun getAllModel(): Flow<List<Model>> {
        return flow { listOf(Model(id = 4787L, name = "Raychel")) }
    }

    override suspend fun updateModel(name: String, id: Long) {

    }

    override fun getOneModel(id: Long): Flow<Model> {
        return flow { Model(id = 4787L, name = "Raychel") }
    }

    override suspend fun delete(id: Long) {

    }

}
