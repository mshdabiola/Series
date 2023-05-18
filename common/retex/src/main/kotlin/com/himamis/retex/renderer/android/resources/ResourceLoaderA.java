package com.himamis.retex.renderer.android.resources;

import android.content.res.AssetManager;

import com.himamis.retex.renderer.share.exception.ResourceParseException;
import com.himamis.retex.renderer.share.platform.resources.ResourceLoader;

import java.io.IOException;
import java.io.InputStream;

public class ResourceLoaderA implements ResourceLoader {

    private AssetManager mAssetManager;

    public ResourceLoaderA(AssetManager assetManager) {
        mAssetManager = assetManager;
    }

    @Override
    public InputStream loadResource(String path) throws ResourceParseException {
        try {
            return mAssetManager.open(path);
        } catch (IOException e) {
            throw new ResourceParseException("Could not load resource.", e);
        }
    }

}
