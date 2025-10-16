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
package com.himamis.retex.renderer.android.graphics

import com.himamis.retex.renderer.share.platform.graphics.BasicStroke
import com.himamis.retex.renderer.share.platform.graphics.Color
import com.himamis.retex.renderer.share.platform.graphics.GraphicsFactory
import com.himamis.retex.renderer.share.platform.graphics.Image
import com.himamis.retex.renderer.share.platform.graphics.Stroke
import com.himamis.retex.renderer.share.platform.graphics.Transform

class GraphicsFactoryAndroid : GraphicsFactory() {
    override fun createBasicStroke(
        width: Double,
        cap: Int,
        join: Int,
        miterLimit: Double,
    ): BasicStroke {
        return BasicStrokeA(width, miterLimit, cap, join)
    }

    override fun createColor(red: Int, green: Int, blue: Int, alpha: Int): Color {
        return ColorA(red, green, blue, alpha)
    }

    override fun createImage(width: Int, height: Int, type: Int): Image {
        return ImageA(width, height, type)
    }

    override fun createTransform(): Transform {
        return TransformA()
    }

    override fun createBasicStroke(width: Double, dashes: DoubleArray): Stroke {
        return BasicStrokeA(width, dashes)
    }
}
