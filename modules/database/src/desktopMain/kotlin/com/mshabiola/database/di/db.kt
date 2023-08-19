package com.mshabiola.database.di

import app.cash.sqldelight.db.QueryResult
import app.cash.sqldelight.driver.jdbc.sqlite.JdbcSqliteDriver
import com.mshdabiola.database.SeriesDatabase
import java.util.Properties

fun withDatabase(path: String): JdbcSqliteDriver {
    return JdbcSqliteDriver(
        "jdbc:sqlite:$path",
        properties = Properties().apply { put("foreign_keys", "true") }
    ).apply {
        migrateIfNeeded(this)
    }

}

private const val versionPragma = "user_version"

fun migrateIfNeeded(driver: JdbcSqliteDriver) {
    val oldVersion =
        driver .executeQuery(null, "PRAGMA $versionPragma", parameters = 0, mapper = { cursor ->
            val res=  if (cursor.next().value) {
                cursor.getLong(0)
            } else {
                null
            }
            QueryResult.Value(res)

        }).value ?: 0


    val newVersion = SeriesDatabase.Schema.version

    if (oldVersion == 0L) {
        println("Creating DB version $newVersion!")
        SeriesDatabase.Schema.create(driver)
        driver.execute(null, "PRAGMA $versionPragma=$newVersion", 0)
    } else if (oldVersion < newVersion) {
        println("Migrating DB from version $oldVersion to $newVersion!")
        SeriesDatabase.Schema.migrate(driver, oldVersion, newVersion)
        driver.execute(null, "PRAGMA $versionPragma=$newVersion", 0)
    }
}
