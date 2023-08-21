package com.mshdabiola.data.repository

import com.mshdabiola.database.SeriesDatabase

expect class DatabaseExportImport( database: SeriesDatabase) {

   suspend fun export(
        examsId : List<Long>,
        path :String,
        name : String,
        version:Int,
        key:String
    )
    suspend fun import(
        path :String,
        key:String
    )
}