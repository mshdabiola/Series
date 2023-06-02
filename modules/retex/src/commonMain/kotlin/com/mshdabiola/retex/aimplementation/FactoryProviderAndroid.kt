package com.mshdabiola.retex.aimplementation

import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.font.FontFactory
import com.himamis.retex.renderer.share.platform.geom.GeomFactory
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory
import com.himamis.retex.renderer.share.platform.parser.ParserFactory
import com.himamis.retex.renderer.share.platform.resources.ResourceLoaderFactory
import com.mshdabiola.retex.aimplementation.font.FontFactoryAndroid
import com.mshdabiola.retex.aimplementation.geom.GeomFactoryAndroid
import com.mshdabiola.retex.aimplementation.graphics.GraphicsFactoryAndroid
import com.mshdabiola.retex.aimplementation.parser.ParserFactoryAndroid
import com.mshdabiola.retex.aimplementation.resources.ResourceLoaderFactoryAndroid
import java.io.File

class FactoryProviderAndroid(val file: File) : FactoryProvider() {


    override fun createGeomFactory(): GeomFactory {
        return GeomFactoryAndroid()
    }

    override fun createFontFactory(): FontFactory {
        return FontFactoryAndroid()
    }

    override fun createGraphicsFactory(): GraphicsFactory {
        return GraphicsFactoryAndroid()
    }

    //@Override
    fun createParserFactory(): ParserFactory {
        return ParserFactoryAndroid()
    }

    //@Override
    override fun createResourceLoaderFactory(): ResourceLoaderFactory {
        return ResourceLoaderFactoryAndroid()
    }
}