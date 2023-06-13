package com.mshdabiola.app

import com.mshdabiola.finishscreen.di.finishModule
import com.mshdabiola.mainscreen.di.mainModule
import com.mshdabiola.profilescreen.di.profileModule
import com.mshdabiola.questionscreen.di.questionModule
import com.mshdabiola.statisticsscreen.di.statModule
import org.koin.dsl.module

val app1Module = module {
    includes(mainModule, profileModule, statModule, questionModule, finishModule)
}