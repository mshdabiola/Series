package com.mshdabiola.question

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val questionModule = module {
    commonViewModel { pa->
        QuestionViewModel(pa[0],pa[1],pa[2],get(), get(),get(),get())
    }
}
