package com.himamis.retex.renderer.android.resources

import android.content.res.AssetManager
import com.himamis.retex.renderer.share.exception.ResourceParseException
import com.himamis.retex.renderer.share.platform.resources.ResourceLoader
import java.io.IOException
import java.io.InputStream

class ResourceLoaderA(private val mAssetManager: AssetManager) : ResourceLoader {
    @Throws(ResourceParseException::class)
    override fun loadResource(path: String): InputStream {
        return try {
            mAssetManager.open(path)
        } catch (e: IOException) {
            throw ResourceParseException("Could not load resource.", e)
        }
    }
}
