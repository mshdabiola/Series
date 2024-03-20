package com.mshabiola.database.di

import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshabiola.database.model.listOfValueAdapter
import com.mshdabiola.database.SeriesDatabase
import com.mshdabiola.model.databaseName
import com.mshdabiola.model.generalPath
import commshdabioladatabase.tables.InstructionEntity
import commshdabioladatabase.tables.OptionEntity
import commshdabioladatabase.tables.QuestionEntity
import org.koin.core.module.Module
import org.koin.core.qualifier.qualifier
import org.koin.dsl.module
import java.io.File
import java.util.Properties

actual val databaseModule: Module
    get() = module {
        single(qualifier = qualifier("real")) {
            val dir = File(generalPath)
            if (dir.exists().not()) {
                dir.mkdirs()
            }
            val dbPath = File(generalPath, databaseName)
//            val os= System.getProperty("os.name")
//            println("os $os")
//            println("path ${System.getProperty("user.home")}")

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
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter,
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter),
            )
        }
        single(qualifier = qualifier("temp")) {
            val driver = JdbcSqliteDriver(
                JdbcSqliteDriver.IN_MEMORY,
                properties = Properties().apply { put("foreign_keys", "true") },
            )
                .also { SeriesDatabase.Schema.create(it) }

            SeriesDatabase(
                driver = driver,
                questionEntityAdapter = QuestionEntity.Adapter(
                    listOfValueAdapter,
                    listOfValueAdapter,
                ),
                instructionEntityAdapter = InstructionEntity.Adapter(listOfValueAdapter),
                optionEntityAdapter = OptionEntity.Adapter(listOfValueAdapter),
            )
        }

        includes(daoModules)
    }

// private var version: Int
//    get() {
//        val sqlCursor = driver.executeQuery(null, "PRAGMA user_version;", 0, null)
//        return sqlCursor.getLong(0)!!.toInt()
//    }
//    private set(version) {
//        driver.execute(null, String.format("PRAGMA user_version = %d;", version), 0, null)
//    }
