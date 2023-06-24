package com.mshabiola.database.di

import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier("real")) {
            val driver = AndroidSqliteDriver(
                schema = SeriesDatabase.Schema,
                context = get(),
                name = Constant.databaseName
            )



            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(listOfValueAdapter),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }
        single(qualifier = qualifier("temp")) {
            val driver = AndroidSqliteDriver(
                schema = SeriesDatabase.Schema,
                context = get(),
                name = null
            )

            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(listOfValueAdapter),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }

        includes(daoModules)
    }