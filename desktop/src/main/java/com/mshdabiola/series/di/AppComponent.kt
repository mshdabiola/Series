package com.mshdabiola.series.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.series.feature.main.MainViewModel
import com.mshdabiola.series.ui.feature.exam.ExamViewModel
import com.mshdabiola.series.ui.feature.splash.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object Modules {


}

val desktopModule = module {

    factoryOf(::SplashViewModel);
    factoryOf(::MainViewModel)
    factoryOf(::ExamViewModel)

    includes(dataModule)
    //singleOf(::MainScreenComponent)
}