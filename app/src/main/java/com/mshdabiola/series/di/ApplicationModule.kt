package com.mshdabiola.series.di


import com.mshdabiola.series.screen.di.screenModule
import org.koin.dsl.module

val appModule = module {
    includes(screenModule)
}