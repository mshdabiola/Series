
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.Density
import com.mshdabiola.ui.image.parseVectorRoot
import org.xml.sax.InputSource
import javax.xml.parsers.DocumentBuilderFactory

/**
 * Synchronously load an xml vector image from some [inputSource].
 *
 * XML Vector Image came from Android world. See:
 * https://developer.android.com/guide/topics/graphics/vector-drawable-resources
 *
 * On desktop it is fully implemented except there is no resource linking
 * (for example, we can't reference to color defined in another file)
 *
 * @param inputSource input source to load xml vector image. Will be closed automatically.
 * @param density density that will be used to set the default size of the ImageVector. If the image
 * will be drawn with the specified size, density will have no effect.
 * @return the decoded vector image associated with the image
 */
fun loadXmlImageVector(
    inputSource: InputSource,
    density: Density,
): ImageVector = DocumentBuilderFactory
    .newInstance().apply {
        isNamespaceAware = true
    }
    .newDocumentBuilder()
    .parse(inputSource)
    .documentElement
    .parseVectorRoot(density)
