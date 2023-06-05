package com.mshdabiola.common

actual val generalPath: String
    get() = System.getProperty("java.io.tmpdir")