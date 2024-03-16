package com.mshdabiola.series.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.mvvn.commonViewModel
import com.mshdabiola.series.navigation.MainAppViewModel
import com.mshdabiola.series.screen.MainViewModel
import com.mshdabiola.series.screen.setting.SettingViewModel
import org.koin.dsl.module

val appModule = module {
    includes(dataModule)
    commonViewModel {
        MainViewModel(get(), get(), get())
    }
    commonViewModel { MainAppViewModel(get()) }
    commonViewModel { SettingViewModel(get()) }

}
