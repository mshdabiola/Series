package com.mshdabiola.series


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.mvvn.commonViewModel
import com.mshdabiola.series.feature.exam.ExamViewModel
import com.mshdabiola.series.feature.main.MainViewModel
import org.koin.dsl.module


val appModules = module {

//    commonViewModel(::SplashViewModel)
    commonViewModel { MainViewModel(get(), get(), get()) }
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