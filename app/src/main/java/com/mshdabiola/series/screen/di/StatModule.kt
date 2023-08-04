package com.mshdabiola.series.screen.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.series.screen.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val screenModule = module {

    includes(dataModule)
    viewModelOf(::MainViewModel)
}
