package com.mshdabiola.stat

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val statModule = module {
    commonViewModel {
        StatViewModel()
    }
}
