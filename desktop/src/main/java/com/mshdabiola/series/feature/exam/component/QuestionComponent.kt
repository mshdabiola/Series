package com.mshdabiola.series.feature.exam.component

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.state.QuestionUiState


@Composable
fun QuestionWholeUi(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    edit: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> }
) {

    Column(modifier) {
        Content(
            items = questionUiState.content,
            editMode = questionUiState.editMode,
            addUp = { addUp(-1, it) },
            addBottom = { addBottom(-1, it) },
            delete = { delete(-1, it) },
            moveUp = { moveUp(-1, it) },
            moveDown = { moveDown(-1, it) },
            edit = { edit(-1, it) },
            changeType = { i, t -> changeType(-1, i, t) },
            onTextChange = { i, s -> onTextChange(-1, i, s) }

        )

        questionUiState.options.chunked(2).forEachIndexed { index1, optionsUiStates ->
            Row {
                optionsUiStates.forEachIndexed { index2, optionsUiState ->
                    val i = index2 + (index1 * 2)
                    Content(
                        modifier = Modifier.weight(0.5f),
                        items = optionsUiState.content,
                        editMode = questionUiState.editMode,
                        addUp = { addUp(i, it) },
                        addBottom = { addBottom(i, it) },
                        delete = { delete(i, it) },
                        moveUp = { moveUp(i, it) },
                        moveDown = { moveDown(i, it) },
                        edit = { edit(i, it) },
                        changeType = { ii, t -> changeType(i, ii, t) },
                        onTextChange = { idn, s -> onTextChange(i, idn, s) }

                    )
                }
            }
        }

    }
}

@Preview
@Composable
fun QuestionWholeUiPreview() {


}
