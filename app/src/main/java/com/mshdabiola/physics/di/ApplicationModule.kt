package com.mshdabiola.physics.di


import com.mshdabiola.physics.screen.di.screenModule
import org.koin.dsl.module

val appModule = module {
    includes(screenModule)
}