package com.mshdabiola.series.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.series.feature.exam.ExamViewModel
import com.mshdabiola.series.feature.main.MainViewModel
import com.mshdabiola.series.feature.splash.SplashViewModel
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object Modules {


}

val desktopModule = module {

    factoryOf(::SplashViewModel);
    factoryOf(::MainViewModel)
    factory {
        ExamViewModel(examId = it[0], subjectId = it[1], questionRepository = get())
    }

    includes(dataModule)
    //singleOf(::MainScreenComponent)
}