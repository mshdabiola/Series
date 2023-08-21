package com.mshdabiola.data.repository

import com.mshdabiola.database.SeriesDatabase

actual class DatabaseExportImport actual constructor(private val database: SeriesDatabase)
{
    actual suspend fun export(
        examsId: List<Long>,
        path: String,
        name:String,
        version: Int,
        key: String
    ) {
    }

    actual suspend fun import(path: String, key: String) {
    }

}