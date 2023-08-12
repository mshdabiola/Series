package com.mshdabiola.util

import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val commonModule = module {
    singleOf(::Converter)
    singleOf(::ExInPort)
    // singleOf(::FileManager)
}