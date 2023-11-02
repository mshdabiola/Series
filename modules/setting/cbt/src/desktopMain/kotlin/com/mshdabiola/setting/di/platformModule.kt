package com.mshdabiola.setting.di

import com.mshdabiola.model.generalPath
import com.mshdabiola.setting.createDataStore
import com.mshdabiola.setting.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

actual val settingModule: Module
    get() = module {
        includes(commonModule)
        single {
            createDataStore { "$generalPath/$dataStoreFileName" }
        }
    }