package com.mshdabiola.ui

import android.graphics.BitmapFactory
import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.retex.Latex
import com.mshdabiola.ui.com.mshdabiola.ui.loadXmlImageVector
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import org.xml.sax.InputSource
import timber.log.Timber
import java.io.File

@Composable
actual fun EquationUi(
    modifier: Modifier,
    equation: ItemUiState
) {
    Latex(modifier = modifier, equation.content)
}

@Composable
actual fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
) {

    val file = remember(path) {
        File(path)
    }

    if (file.extension == "xml") {
        VectorImage(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    } else {
        BitmapImage(
            modifier = modifier,
            path = path,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }


}

@Composable
fun VectorImage(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
) {
    val context = LocalContext.current
    val density = LocalDensity.current
    var image by remember {
        mutableStateOf<ImageVector?>(null)
    }

    LaunchedEffect(key1 = path, block = {
        try {


            val instream = context.assets.open(path)
            image = instream.buffered().use { loadXmlImageVector(InputSource(it), density) }

        } catch (e: Exception) {
            Timber.e(e)
        }
    })


    if (image != null) {
        Image(
            modifier = modifier,
            imageVector = image!!,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }

}

@Composable
fun BitmapImage(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
) {
    val context = LocalContext.current
    var image by remember {
        mutableStateOf<ImageBitmap?>(null)
    }

    LaunchedEffect(key1 = path, block = {
        try {

            image = BitmapFactory
                .decodeStream(context.assets.open(path))
                .asImageBitmap()

        } catch (e: Exception) {
            Timber.e(e)
        }
    })


    if (image != null) {
        Image(
            modifier = modifier,
            bitmap = image!!,
            contentDescription = contentDescription,
            contentScale = contentScale
        )
    }
}

@Composable
actual fun DragAndDropImage(
    modifier: Modifier,
    path: String,
    onPathChange: (String) -> Unit
) {
}

@Preview
@Composable
internal actual fun QuestionUiPreview() {
    val questionUiState = QuestionUiState(
        id = 1,
        nos = 1,
        examId = 1,
        content = listOf(
            ItemUiState(content = "What is your name")
        )
            .toImmutableList(),
        options = listOf(
            OptionUiState(
                id = 1, nos = 1, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false
            ),
            OptionUiState(
                id = 2, nos = 2,
                content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false,
            ),
            OptionUiState(
                id = 3, nos = 3, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false
            ),
            OptionUiState(
                id = 4, nos = 4,
                content = listOf(
                    ItemUiState(
                        content = "Isabelle",
                    )
                ).toImmutableList(),
                isAnswer = false,
            )
        ).toImmutableList(),
        instructionUiState = InstructionUiState(
            id = 1,
            examId = 3,
            title = "What",
            content = listOf(ItemUiState()).toImmutableList()
        )
    )
    QuestionUi(
        number = 1,
        questionUiState = questionUiState,
    )
}

@Preview
@Composable
internal actual fun OptionsUiPreview() {

    OptionsUi(
        optionUiStates = listOf(
            OptionUiState(
                id = 1, nos = 1,
                content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false,
            ),
            OptionUiState(
                id = 2, nos = 2, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false
            ),
            OptionUiState(
                id = 3, nos = 3,
                content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false,
            ),
            OptionUiState(
                id = 4, nos = 4, content = listOf(
                    ItemUiState(
                        content = "Isabelle",
                    )
                ).toImmutableList(),
                isAnswer = false
            )
        ).toImmutableList(), examId =4
    )
}

@Preview
@Composable
internal actual fun QuestionHeadUiPreview() {
    QuestionHeadUi(
        number = 1,
        title = "Waec 1993 Q2",
        content = listOf(ItemUiState("What is you name")).toImmutableList(),
        isInstruction = true,
        examID = 4
    )
}