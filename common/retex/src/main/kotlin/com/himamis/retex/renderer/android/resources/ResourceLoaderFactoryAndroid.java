package com.himamis.retex.renderer.android.resources;

import android.content.res.AssetManager;

import com.himamis.retex.renderer.share.platform.resources.ResourceLoader;
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory;

public class ResourceLoaderFactoryAndroid implements ResourceLoaderFactory {

    private AssetManager mAssetManager;

    public ResourceLoaderFactoryAndroid(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    public ResourceLoader createResourceLoader() {
        return new ResourceLoaderA(mAssetManager);
    }

}
