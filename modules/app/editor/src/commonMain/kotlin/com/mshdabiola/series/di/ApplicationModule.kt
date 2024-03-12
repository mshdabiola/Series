package com.mshdabiola.series.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.mvvn.commonViewModel
import com.mshdabiola.series.navigation.MainAppViewModel
import com.mshdabiola.series.screen.exam.ExamViewModel
import com.mshdabiola.series.screen.main.MainViewModel
import org.koin.dsl.module


val appModules= module {

//    commonViewModel(::SplashViewModel)
    commonViewModel { MainViewModel(get(), get(), get()) }
    commonViewModel { MainAppViewModel(get()) }

//    factoryOf(::MainViewModel)
    commonViewModel {
        ExamViewModel(
            examId = it[0],
            subjectId = it[1],
            questionRepository = get(),
            instructionRepository = get(),
            topicRepository = get(),
            settingRepository = get(),
            examRepository = get()
            //fileManager = get()

        )
    }

    includes(dataModule)
    //singleOf(::MainScreenComponent)
}