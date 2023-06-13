package com.mshdabiola.finishscreen.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.finishscreen.FinishViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val finishModule = module {

    includes(dataModule)
    viewModelOf(::FinishViewModel)
}
