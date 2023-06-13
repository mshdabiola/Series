package com.mshdabiola.questionscreen.di


import com.mshdabiola.data.di.dataModule
import com.mshdabiola.questionscreen.QuestionViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val questionModule = module {

    includes(dataModule)
    viewModelOf(::QuestionViewModel)
}
