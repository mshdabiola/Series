package com.himamis.retex.renderer.android.resources

import android.content.res.AssetManager
import com.himamis.retex.renderer.share.platform.resources.ResourceLoader
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory

class ResourceLoaderFactoryAndroid(private val mAssetManager: AssetManager) :
    ResourceLoaderFactory {
    override fun createResourceLoader(): ResourceLoader {
        return ResourceLoaderA(mAssetManager)
    }
}
