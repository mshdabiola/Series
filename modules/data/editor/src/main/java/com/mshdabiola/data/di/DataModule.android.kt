package com.mshdabiola.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.android.AndroidSqliteDriver
import com.mshdabiola.database.SeriesDatabase
import org.koin.core.module.Module
import org.koin.dsl.module

actual val newModule: Module
    get() = module {
        single<SqlDriver> {
            AndroidSqliteDriver(schema = SeriesDatabase.Schema, context = get(), "data.db")
        }
    }
