package com.mshdabiola.ui

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.tooling.preview.Preview
import com.mshdabiola.retex.aimplementation.LatexText
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList

@Composable
internal actual fun EquationUi(
    modifier: Modifier,
    equation: ItemUiState
) {
    val context = LocalContext.current
    LatexText(modifier = Modifier, equation.content) { Font(it, assetManager = context.assets) }
}

@Composable
actual fun ImageUi(
    modifier: Modifier,
    path: String,
    contentDescription: String,
    contentScale: ContentScale
) {
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
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 2, nos = 2, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 3, nos = 3, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 4, nos = 4, content = listOf(
                    ItemUiState(
                        content = "Isabelle",
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
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
        title = "Waec"
    )
}

@Preview
@Composable
internal actual fun OptionsUiPreview() {

    OptionsUi(
        optionUiStates = listOf(
            OptionUiState(
                id = 1, nos = 1, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 2, nos = 2, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 3, nos = 3, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
            ),
            OptionUiState(
                id = 4, nos = 4, content = listOf(
                    ItemUiState(
                        content = "Isabelle",
                    )
                ).toImmutableList(),
                isAnswer = false, choose = false
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