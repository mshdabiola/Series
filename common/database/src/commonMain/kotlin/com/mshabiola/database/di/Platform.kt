package com.mshabiola.database.di



import com.mshabiola.database.dao.exam.ExamDao
import com.mshabiola.database.dao.exam.IExamDao
import com.mshabiola.database.dao.instructiondao.IInstructionDao
import com.mshabiola.database.dao.instructiondao.InstructionDao
import com.mshabiola.database.dao.optiondao.IOptionDao
import com.mshabiola.database.dao.optiondao.OptionDao
import com.mshabiola.database.dao.questiondao.IQuestionDao
import com.mshabiola.database.dao.questiondao.QuestionDao
import com.mshabiola.database.dao.subjectdao.ISubjectDao
import com.mshabiola.database.dao.subjectdao.SubjectDao
import com.mshabiola.database.dao.topicdao.ITopicDao
import com.mshabiola.database.dao.topicdao.TopicDao
import com.mshdabiola.database.SeriesDatabase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

expect val databaseModule: Module


internal val daoModules = module {


    single {
        get<SeriesDatabase>().instructionQueries
    }
    single {
        get<SeriesDatabase>().optionQueries
    }
    single {
        get<SeriesDatabase>().questionQueries
    }
    single {
        get<SeriesDatabase>().subjectQueries
    }
    single {
        get<SeriesDatabase>().topicQueries
    }
    single {
        get<SeriesDatabase>().examQueries
    }
    single { Dispatchers.IO } bind CoroutineDispatcher::class
    singleOf(::InstructionDao) bind IInstructionDao::class
    singleOf(::OptionDao) bind IOptionDao::class
    singleOf(::QuestionDao) bind IQuestionDao::class
    singleOf(::SubjectDao) bind ISubjectDao::class
    singleOf(::TopicDao) bind ITopicDao::class
    singleOf(::ExamDao) bind IExamDao::class
}
