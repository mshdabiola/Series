package com.mshdabiola.statisticsscreen.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.statisticsscreen.StatViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val statModule = module {

    includes(dataModule)
    viewModelOf(::StatViewModel)
}
