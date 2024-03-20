package com.mshdabiola.data.di

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import org.koin.core.module.Module
import org.koin.dsl.module

actual val newModule: Module
    get() = module {
        single<SqlDriver> {
            JdbcSqliteDriver("")
        }
    }
