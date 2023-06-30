package com.mshdabiola.worker.di

import com.mshdabiola.util.ExInPort
import com.mshdabiola.util.FileManager
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


val workModule = module {

    //workerOf(::SaveWorker)
    singleOf(::ExInPort)
    singleOf(::FileManager)
}