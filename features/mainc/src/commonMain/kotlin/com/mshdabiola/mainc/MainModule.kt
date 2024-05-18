package com.mshdabiola.mainc

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val mainModule = module {
    commonViewModel {
        MainViewModel(get(), get(),get(),get())
    }
}
