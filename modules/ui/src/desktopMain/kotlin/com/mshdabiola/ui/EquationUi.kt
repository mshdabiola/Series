package com.mshdabiola.ui

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.Dp
import com.mshdabiola.retex.aimplementation.LatexText
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList

@Composable
internal actual fun EquationUi(
    modifier: Modifier ,
    equation: ItemUiState
) {
    LatexText(modifier = Modifier, equation.content) { Font(it) }
}


@Composable
internal actual fun QuestionUiPreview() {
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
                isAnswer = false, choose = true
            ),
            OptionUiState(
                id = 2, nos = 2, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = true, choose = true
            ),
            OptionUiState(
                id = 3, nos = 3, content = listOf(
                    ItemUiState(
                        content = "Isabelle"
                    )
                ).toImmutableList(),
                isAnswer = true, choose = false
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
fun Preview() {
    Card (
        colors = CardDefaults.cardColors(contentColor = Color.Red)
    ){
        Column {
            Row {Text("Aviola")  }

        }
    }
}
