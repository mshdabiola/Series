package com.mshdabiola.profilescreen.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.profilescreen.ProfileViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val profileModule = module {

    includes(dataModule)
    viewModelOf(::ProfileViewModel)
}
