import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import com.mshdabiola.retex.aimplementation.Formulae
import com.mshdabiola.retex.aimplementation.Latex
import kotlinx.collections.immutable.toImmutableList


@Preview
@Composable
fun LatexPreview() {


    val basic = remember { Formulae() }
    val equations = basic.getShapes("\\sqrt[\\frac{4}{38}]{\\frac{168}{6}+78\\frac{1}{2}}")
        .toImmutableList()
    Latex(modifier = Modifier.size(500.dp).background(Color.Red), scale = 3f,equations = equations) { Font(it) }
}