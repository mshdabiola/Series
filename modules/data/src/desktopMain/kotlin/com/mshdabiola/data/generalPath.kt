package com.mshdabiola.data

internal actual val generalPath: String
    get() = System.getProperty("java.io.tmpdir")
