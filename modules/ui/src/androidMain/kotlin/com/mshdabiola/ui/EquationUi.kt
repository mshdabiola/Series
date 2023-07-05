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
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.graphics.nativeCanvas
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.graphics.toRect
import com.caverock.androidsvg.SVG
import com.mshdabiola.retex.aimplementation.LatexText
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import timber.log.Timber
import java.io.File

@Composable
actual fun EquationUi(
    modifier: Modifier,
    equation: ItemUiState
) {
    val context = LocalContext.current
    LatexText(modifier = modifier, equation.content) { Font(it, assetManager = context.assets) }
}

@Composable
actual fun ImageUi(
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
            val file = File(path)
            Timber.e("extention ${file.extension}")
            image = when (file.extension) {
                "svg" -> {
                    val svg = SVG.getFromAsset(context.assets, path)

                    val rect = svg.documentViewBox.toRect()

                    val imageBitmap = ImageBitmap(rect.width(), rect.height())
                    val canvas = Canvas(imageBitmap)
                    svg.renderToCanvas(canvas.nativeCanvas)
                    canvas.save()
                    imageBitmap
                }

                else -> {
                    BitmapFactory
                        .decodeStream(context.assets.open(path))
                        .asImageBitmap()
                }
            }
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
        generalPath = "",
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
        ).toImmutableList(), generalPath = ""
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
        generalPath = ""
    )
}