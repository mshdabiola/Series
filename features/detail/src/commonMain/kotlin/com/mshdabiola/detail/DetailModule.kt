package com.mshdabiola.detail

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val detailModule = module {
    commonViewModel { param ->
        DetailViewModel(param[0], param[1], get(), get(), get(), get(), get())
    }
}
