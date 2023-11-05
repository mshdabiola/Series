package com.mshdabiola.data.di

import com.mshabiola.database.di.databaseModule
import com.mshdabiola.data.repository.ExamRepository
import com.mshdabiola.data.repository.QuestionRepository
import com.mshdabiola.data.repository.SettingRepository
import com.mshdabiola.data.repository.SubjectRepository
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.setting.di.settingModule
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    includes(settingModule, databaseModule)
    singleOf(::SettingRepository) bind ISettingRepository::class
    // singleOf(::RealINetworkRepository) bind INetworkRepository::class
    singleOf(::SubjectRepository) bind ISubjectRepository::class
    singleOf(::QuestionRepository) bind IQuestionRepository::class
    singleOf(::ExamRepository) bind IExamRepository::class
}
