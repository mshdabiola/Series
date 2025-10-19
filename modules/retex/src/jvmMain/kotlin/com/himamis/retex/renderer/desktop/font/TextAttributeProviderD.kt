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
package com.himamis.retex.renderer.desktop.font

import com.himamis.retex.renderer.share.platform.font.TextAttribute
import com.himamis.retex.renderer.share.platform.font.TextAttributeProvider

class TextAttributeProviderD : TextAttributeProvider {
    override fun getTextAttribute(name: String): TextAttribute? {
        return try { // to avoid problems with Java 1.5
            TextAttributeD(
                TextAttribute::class.java
                    .getField(name)[TextAttribute::class.java] as java.awt.font.TextAttribute,
            )
        } catch (e: Exception) {
            null
        }
    }

    override fun getTextAttributeValue(name: String): Int? {
        return try {
            TextAttribute::class.java.getField(name)[TextAttribute::class.java] as Int
        } catch (e: Exception) {
            null
        }
    }
}
