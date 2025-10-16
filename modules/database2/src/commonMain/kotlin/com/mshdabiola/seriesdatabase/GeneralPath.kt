package com.mshdabiola.seriesdatabase

expect var generalPath: String

var callback: Callback? = null

abstract class Callback {
    abstract fun onCreate(connection: androidx.sqlite.SQLiteConnection, path: String)

    abstract fun onDestructiveMigration(connection: androidx.sqlite.SQLiteConnection)

    abstract fun onOpen(connection: androidx.sqlite.SQLiteConnection)
}
