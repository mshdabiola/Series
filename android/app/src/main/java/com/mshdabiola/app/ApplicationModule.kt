package com.mshdabiola.app

import com.mshdabiola.detail.di.detailModule
import com.mshdabiola.mainscreen.di.mainModule
import org.koin.dsl.module

val app1Module = module {
    includes(mainModule, detailModule)
}