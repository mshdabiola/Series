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
package com.himamis.retex.renderer.desktop

import com.himamis.retex.renderer.desktop.box.ShapeBoxDecorator
import com.himamis.retex.renderer.desktop.font.FontFactoryDesktop
import com.himamis.retex.renderer.desktop.geom.GeomFactoryDesktop
import com.himamis.retex.renderer.desktop.graphics.GraphicsFactoryDesktop
import com.himamis.retex.renderer.share.platform.FactoryProvider
import com.himamis.retex.renderer.share.platform.box.BoxDecorator
import com.himamis.retex.renderer.share.platform.font.FontFactory
import com.himamis.retex.renderer.share.platform.geom.GeomFactory
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory

class FactoryProviderDesktop : FactoryProvider() {
    override fun createFontFactory(): FontFactory {
        return FontFactoryDesktop()
    }

    override fun createGeomFactory(): GeomFactory {
        return GeomFactoryDesktop()
    }

    override fun createGraphicsFactory(): GraphicsFactory {
        return GraphicsFactoryDesktop()
    }

    override fun createBoxDecorator(): BoxDecorator {
        return ShapeBoxDecorator()
    }
}
