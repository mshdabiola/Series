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
