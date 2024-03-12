package com.mshdabiola.data.di

import com.mshabiola.database.di.databaseModule
import com.mshabiola.database.di.name
import com.mshdabiola.analytics.di.analyticsModule
import com.mshdabiola.data.repository.ExamRepository
import com.mshdabiola.data.repository.InstructionRepository
import com.mshdabiola.data.repository.OfflineFirstUserDataRepository
import com.mshdabiola.data.repository.QuestionRepository
import com.mshdabiola.data.repository.RealModelRepository
import com.mshdabiola.data.repository.SettingRepository
import com.mshdabiola.data.repository.SubjectRepository
import com.mshdabiola.data.repository.TopicRepository
import com.mshdabiola.data.repository.UserDataRepository
import com.mshdabiola.data.repository.inter.IExamRepository
import com.mshdabiola.data.repository.inter.IInstructionRepository
import com.mshdabiola.data.repository.inter.IModelRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ISettingRepository
import com.mshdabiola.data.repository.inter.ISubjectRepository
import com.mshdabiola.data.repository.inter.ITopicRepository
import com.mshdabiola.setting.di.settingModule
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.qualifier
import org.koin.dsl.bind
import org.koin.dsl.module


val dataModule = module {
    includes(settingModule, databaseModule, newModule, analyticsModule)
    singleOf(::SettingRepository) bind ISettingRepository::class
    // singleOf(::RealINetworkRepository) bind INetworkRepository::class
    singleOf(::RealModelRepository) bind IModelRepository::class
    singleOf(::SubjectRepository) bind ISubjectRepository::class
    singleOf(::QuestionRepository) bind IQuestionRepository::class
    singleOf(::InstructionRepository) bind IInstructionRepository::class
    singleOf(::TopicRepository) bind ITopicRepository::class
    singleOf(::OfflineFirstUserDataRepository) bind UserDataRepository::class

    single {
        ExamRepository(
            iExamDao = get(),
            database = get(qualifier = qualifier(name)),
            driver = get()
        )
    } bind IExamRepository::class
}

expect val newModule: Module