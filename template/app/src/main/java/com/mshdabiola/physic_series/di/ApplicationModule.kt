package com.mshdabiola.physic_series.di

import com.mshdabiola.app.app1Module
import org.koin.dsl.module

val appModule = module {
    includes(app1Module)
}