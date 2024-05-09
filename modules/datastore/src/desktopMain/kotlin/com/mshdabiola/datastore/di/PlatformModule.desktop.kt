package com.mshdabiola.datastore.di

import com.mshdabiola.datastore.createDataStoreCurrentExam
import com.mshdabiola.datastore.createDataStoreInstruction
import com.mshdabiola.datastore.createDataStoreQuestion
import com.mshdabiola.datastore.createDataStoreUserData
import com.mshdabiola.model.generalPath
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

actual val datastoreModule: Module
    get() = module {
        includes(commonModule)

        single(qualifier = qualifier("userdata")) {
            createDataStoreUserData { "$generalPath/userdataE" }
        }

        single(qualifier = qualifier("question")) {
            createDataStoreQuestion { "$generalPath/questions" }
        }

        single(qualifier = qualifier("instruction")) {
            createDataStoreInstruction { "$generalPath/instructions" }
        }

        single(qualifier = qualifier("current")) {
            createDataStoreCurrentExam { "$generalPath/current" }
        }
    }
