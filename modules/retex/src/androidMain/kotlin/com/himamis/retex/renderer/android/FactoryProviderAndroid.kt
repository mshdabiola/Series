/*
 * Designed and developed by 2024 mshdabiola (lawal abiola)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.himamis.retex.renderer.android

import android.content.res.AssetManager
import com.himamis.retex.renderer.android.font.FontFactoryAndroid
import com.himamis.retex.renderer.android.geom.GeomFactoryAndroid
import com.himamis.retex.renderer.android.graphics.GraphicsFactoryAndroid
import com.himamis.retex.renderer.android.parser.ParserFactoryAndroid
import com.himamis.retex.renderer.android.resources.ResourceLoaderFactoryAndroid
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.font.FontFactory
import com.himamis.retex.renderer.share.platform.geom.GeomFactory
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory
import com.himamis.retex.renderer.share.platform.parser.ParserFactory
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory

class FactoryProviderAndroid(private val mAssetManager: AssetManager) : FactoryProvider() {
    override fun createGeomFactory(): GeomFactory {
        return GeomFactoryAndroid()
    }

    override fun createFontFactory(): FontFactory {
        return FontFactoryAndroid(mAssetManager)
    }

    override fun createGraphicsFactory(): GraphicsFactory {
        return GraphicsFactoryAndroid()
    }

    // @Override
    protected fun createParserFactory(): ParserFactory {
        return ParserFactoryAndroid()
    }

    // @Override
    override fun createResourceLoaderFactory(): ResourceLoaderFactory {
        return ResourceLoaderFactoryAndroid(mAssetManager)
    }
}
