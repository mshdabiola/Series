import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.dp
import com.mshdabiola.retex.aimplementation.Formulae
import com.mshdabiola.retex.aimplementation.Latex
import kotlinx.collections.immutable.toImmutableList


@Preview
@Composable
fun LatexPreview() {


    val basic = remember { Formulae() }
    val equations = basic.getShapes("\\sqrt[\\frac{4}{3}]{\\frac{16}{6}+78\\frac{1}{2}}")
        .toImmutableList()
    Latex(modifier = Modifier.size(400.dp), equations = equations) { Font(it) }
}