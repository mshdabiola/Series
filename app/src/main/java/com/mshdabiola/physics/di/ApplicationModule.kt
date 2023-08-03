package com.mshdabiola.physics.di


import com.mshdabiola.screen.di.screenModule
import org.koin.dsl.module

val appModule = module {
    includes(screenModule)
}