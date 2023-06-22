package com.mshdabiola.screen.finish

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Replay
import androidx.compose.material.icons.filled.Share
import androidx.compose.material.icons.filled.WineBar
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mshdabiola.screen.MainViewModel
import com.mshdabiola.ui.FinishCard
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.ScoreCard
import com.mshdabiola.ui.com.mshdabiola.ui.InstructionBottomSheet
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun FinishScreen(onBack: () -> Unit, toQuestion: () -> Unit, viewModel: MainViewModel) {

    val questions = viewModel.questionsList.collectAsStateWithLifecycle()
    FinishScreen(
        questions = questions.value,
        back = onBack,
        toQuestion = toQuestion
    )
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun FinishScreen(
    questions: ImmutableList<QuestionUiState>,
    back: () -> Unit = {},
    toQuestion: () -> Unit = {}
) {
    val lazyState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()
    var showAnswer by remember {
        mutableStateOf(false)
    }
    var instructionUiState by remember {
        mutableStateOf<InstructionUiState?>(null)
    }

    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true },
        bottomBar = {
            BottomAppBar(
                actions = {
                    IconButton(onClick = back) {
                        Icon(
                            imageVector = Icons.Default.ArrowBackIosNew,
                            contentDescription = "back"
                        )
                    }
                    IconButton(onClick = { /*TODO*/ }) {
                        Icon(imageVector = Icons.Default.Share, contentDescription = "Share")
                    }

                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = toQuestion) {
                        Icon(imageVector = Icons.Default.Replay, contentDescription = "Share")
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Retry Questions")
                    }
                }
            )
        }
    ) { paddingValues ->
        LazyColumn(
            Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(horizontal = 8.dp),
            state = lazyState,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            item {
                Text(
                    text = "Good Job",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.primary
                )
            }
            item {
                FinishCard(
                    imageVector = Icons.Default.WineBar,
                    percent = 54,
                    isHide = !showAnswer,
                    onShowAnswers = {
                        showAnswer = !showAnswer
                        if (showAnswer) {
                            coroutineScope.launch {
                                lazyState.scrollToItem(3)
                            }
                        }

                    })
            }
            item {
                ScoreCard()
            }
            if (showAnswer) {
                itemsIndexed(items = questions) { index, item ->
                    QuestionUi(
                        number = (index + 1L),
                        questionUiState = item,
                        generalPath = "",
                        title = "Waec 2015 Q4",
                        onInstruction = {
                            instructionUiState = item.instructionUiState
                        },
                        onOptionClick = {
                        },
                        showAnswer = true
                    )
                }
            }


        }

    }
    InstructionBottomSheet(
        instructionUiState = instructionUiState,
        generalPath = "",
        onDismissRequest = { instructionUiState = null }
    )

}

@Preview
@Composable
fun FinishScreenPreview() {
    val questions =
        listOf(
            QuestionUiState(
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
                        isAnswer = false, choose = true
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
            ),
            QuestionUiState(
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
                        isAnswer = false, choose = true
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
            ),
            QuestionUiState(
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
                        isAnswer = false, choose = true
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
        ).toImmutableList()
    FinishScreen(
        questions = questions
    )
}
