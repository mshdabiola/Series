package com.mshdabiola.physic_series.di

import com.mshdabiola.detail.di.detailModule
import com.mshdabiola.mainscreen.di.mainModule
import org.koin.dsl.module

val appModule= module {
    includes(mainModule,detailModule)
}