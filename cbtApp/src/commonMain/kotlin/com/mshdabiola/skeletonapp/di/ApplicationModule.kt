package com.mshdabiola.skeletonapp.di

import com.mshdabiola.data.di.dataModule
import com.mshdabiola.finish.finishModule
import com.mshdabiola.mainc.mainModule
import com.mshdabiola.mvvn.commonViewModel
import com.mshdabiola.profile.profileModule
import com.mshdabiola.question.questionModule
import com.mshdabiola.setting.settingModule
import com.mshdabiola.skeletonapp.MainAppViewModel
import com.mshdabiola.stat.statModule
import org.koin.dsl.module

val appModule = module {
    includes(
        dataModule,
        mainModule,
        statModule,
        profileModule,
        questionModule,
        finishModule,
        settingModule
    )
    commonViewModel { MainAppViewModel(get()) }
}
