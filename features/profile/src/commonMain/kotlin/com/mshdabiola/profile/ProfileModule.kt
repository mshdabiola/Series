package com.mshdabiola.profile

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val profileModule = module {
    commonViewModel {
        ProfileViewModel()
    }
}
