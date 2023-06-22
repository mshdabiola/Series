package com.mshdabiola.ui.com.mshdabiola.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.ItemUi
import com.mshdabiola.ui.QuestionNumberButton
import com.mshdabiola.ui.state.InstructionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlin.random.Random


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AllQuestionBottomSheet(
    show: Boolean,
    chooses: ImmutableList<Boolean>,
    onChooseClick: (Int) -> Unit = {},
    onDismissRequest: () -> Unit = {}
) {
    if (show) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "All questions")

                LazyVerticalGrid(
                    modifier = Modifier.fillMaxWidth(),
                    columns = GridCells.Adaptive(50.dp),
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalArrangement = Arrangement.spacedBy(4.dp),
                    content = {
                        itemsIndexed(items = chooses) { index, item ->
                            QuestionNumberButton(
                                number = index,
                                isChoose = item,
                                onClick = { onChooseClick(index) })
                        }
                    })
            }


        }


    }

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InstructionBottomSheet(
    instructionUiState: InstructionUiState?,
    generalPath: String,
    onDismissRequest: () -> Unit = {}
) {
    if (instructionUiState != null) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Instructions")

                if (instructionUiState.title != null) {
                    Text(text = instructionUiState.title)
                }
                ItemUi(items = instructionUiState.content, generalPath = generalPath)

            }


        }

    }
}


@Preview
@Composable
fun AllQuestionButtonsPreview() {
    val choose = (1..50)
        .map { Random(4).nextBoolean() }


    AllQuestionBottomSheet(show = true, chooses = choose.toImmutableList())
}
