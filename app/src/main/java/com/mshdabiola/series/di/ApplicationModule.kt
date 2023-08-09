package com.mshdabiola.series.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.series.screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    viewModelOf(::MainViewModel)
}