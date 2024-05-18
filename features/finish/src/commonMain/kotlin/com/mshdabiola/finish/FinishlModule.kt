package com.mshdabiola.finish

import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val finishModule = module {
    commonViewModel {
        FinishViewModel(get(), get(),get(),get())
    }
}
