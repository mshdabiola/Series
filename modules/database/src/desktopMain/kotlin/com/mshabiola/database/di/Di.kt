package com.mshabiola.database.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshabiola.database.util.Constant
import com.mshdabiola.database.SeriesDatabase
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.io.File

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier("real")) {
            val dbPath = File(System.getProperty("java.io.tmpdir"), Constant.databaseName)

            val driver = withDatabase(dbPath.path)
//
//            val driver = JdbcSqliteDriver(url = "jdbc:sqlite:${dbPath.absolutePath}")
//                .also {
//                    TempDatabase.Schema.create(it)
//                    println("file  ${dbPath.path}")
//                }
//            println("version ${TempDatabase.Schema.version}")

            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(listOfValueAdapter),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }
        single(qualifier = qualifier("temp")) {
            val driver = JdbcSqliteDriver(JdbcSqliteDriver.IN_MEMORY)
                .also { SeriesDatabase.Schema.create(it) }

            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(listOfValueAdapter),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter)
            )
        }

        includes(daoModules)

    }

//private var version: Int
//    get() {
//        val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
//        return sqlCursor.getLong(0)!!.toInt()
//    }
//    private set(version) {
//        driver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//    }