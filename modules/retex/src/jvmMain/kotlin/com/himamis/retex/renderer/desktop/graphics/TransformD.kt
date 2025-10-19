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
package com.himamis.retex.renderer.desktop.graphics

import com.himamis.retex.renderer.share.platform.graphics.Transform
import java.awt.geom.AffineTransform
import java.awt.geom.NoninvertibleTransformException

class TransformD : AffineTransform, Transform {
    constructor()
    constructor(transform: AffineTransform?) : super(transform)

    override fun createClone(): Transform {
        return TransformD(this)
    }

    val nativeObject: Any
        get() = this

    fun createInverseX(): Transform {
        return try {
            TransformD(super.createInverse())
        } catch (e: NoninvertibleTransformException) {
            TransformD(AffineTransform())
        }
    }

    fun concatenate(Tx: Transform?) {
        super.concatenate(Tx as AffineTransform?)
    }

    companion object {
        private const val serialVersionUID = 1L
    }
}
