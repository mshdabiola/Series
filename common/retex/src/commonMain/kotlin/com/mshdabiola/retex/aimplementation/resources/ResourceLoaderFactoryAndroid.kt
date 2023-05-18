package com.mshdabiola.retex.aimplementation.resources

import com.himamis.retex.renderer.share.platform.resources.ResourceLoader
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory


class ResourceLoaderFactoryAndroid() : ResourceLoaderFactory {

    override fun createResourceLoader(): ResourceLoader {
        return ResourceLoaderA()
    }
}