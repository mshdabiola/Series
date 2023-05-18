package com.mshdabiola.retex.aimplementation.resources


import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.resources.ResourceLoader
import java.io.File
import java.io.IOException
import java.io.InputStream

class ResourceLoaderA() : ResourceLoader {

    @Throws(ResourceParseException::class)
    override fun loadResource(path: String): InputStream {
        return try {
            File(path).inputStream()
        } catch (e: IOException) {
            e.printStackTrace()
            throw ResourceParseException("Could not load resource.", e)
        }
    }
}