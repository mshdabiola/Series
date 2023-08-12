package com.mshdabiola.util

actual val generalPath: String
    get() = System.getProperty("java.io.tmpdir")