package com.mshdabiola.setting.di
import android.content.Context
import com.mshdabiola.setting.createDataStore
import com.mshdabiola.setting.dataStoreFileName
import org.koin.core.module.Module
import org.koin.dsl.module

actual val settingModule: Module
    get() = module {
        includes(commonModule)
        single {
            val context: Context = get()

            createDataStore { context.filesDir.resolve(dataStoreFileName).absolutePath }
        }
    }