package com.mshdabiola.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.datastore.core.okio.OkioStorage
import com.mshdabiola.datastore.model.CurrentExamJsonSerializer
import com.mshdabiola.datastore.model.CurrentExamSer
import com.mshdabiola.datastore.model.InstructionJsonSerializer
import com.mshdabiola.datastore.model.InstructionSer
import com.mshdabiola.datastore.model.QuestionJsonSerializer
import com.mshdabiola.datastore.model.QuestionSer
import com.mshdabiola.datastore.model.UserDataJsonSerializer
import com.mshdabiola.datastore.model.UserDataSer
import okio.FileSystem
import okio.Path.Companion.toPath


fun createDataStoreUserData(
    producePath: () -> String,
): DataStore<UserDataSer> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = UserDataJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)


fun createDataStoreInstruction(
    producePath: () -> String,
): DataStore<Map<Long, InstructionSer>> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = InstructionJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)


fun createDataStoreQuestion(
    producePath: () -> String,
): DataStore<Map<Long, QuestionSer>> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = QuestionJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)


fun createDataStoreCurrentExam(
    producePath: () -> String,
): DataStore<CurrentExamSer> = DataStoreFactory.create(
    storage = OkioStorage(
        fileSystem = FileSystem.SYSTEM,
        serializer = CurrentExamJsonSerializer,
        producePath = {
            producePath().toPath()
        },
    ),
)
