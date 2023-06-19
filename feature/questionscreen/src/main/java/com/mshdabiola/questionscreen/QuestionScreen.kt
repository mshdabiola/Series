package com.mshdabiola.questionscreen

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTagsAsResourceId
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.ItemUi
import com.mshdabiola.ui.QuestionNumberButton
import com.mshdabiola.ui.QuestionScroll
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.TimeCounter
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.koin.androidx.compose.koinViewModel
import kotlin.random.Random

@Composable
internal fun QuestionScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit
) {
    val viewModel: QuestionViewModel = koinViewModel()
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

    QuestionScreen(
        questions = questions,
        profileState = ProfileState(),
        back = onBack,
        onFinish = onFinish
    )
}

@OptIn(
    ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
internal fun QuestionScreen(
    questions: ImmutableList<QuestionUiState>,
    profileState: ProfileState,
    back: () -> Unit = {},
    onFinish: () -> Unit = {}
) {

    var show by remember {
        mutableStateOf(false)
    }
    var showInstruct by remember {
        mutableStateOf(false)
    }
    val coroutineScope = rememberCoroutineScope()
    val state = rememberPagerState {
        questions.size
    }
    val chooses = remember(questions) {
        questions.map { questionUiState ->
            questionUiState.options.any { it.choose }
        }.toImmutableList()
    }
    val finishPercent = remember(chooses) {
        ((chooses.count {
            it
        } / chooses.size.toFloat()) * 100).toInt()
    }


    Scaffold(
        modifier = Modifier.semantics { this.testTagsAsResourceId = true }

    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimeCounter(currentTime = 400, total = 500)

            HorizontalPager(state = state) {
                QuestionUi(
                    number = (it + 1L),
                    questionUiState = questions[it],
                    generalPath = "",
                    title = "Waec 2015 Q4",
                    onInstruction = {
                        showInstruct=true
                    },
                    onOptionClick = {}
                )
            }
            QuestionScroll(
                currentQuestion = state.currentPage,
                showPrev = state.canScrollBackward,
                showNext = state.canScrollForward,
                chooses = chooses,
                onChooseClick = {
                    coroutineScope.launch {
                        state.animateScrollToPage(it)
                    }
                },
                onShowAllClick = { show = true },
                onNext = {
                    coroutineScope.launch {
                        state.animateScrollToPage(state.currentPage + 1)
                    }
                },
                onPrev = {
                    coroutineScope.launch {
                        state.animateScrollToPage(state.currentPage - 1)
                    }
                }
            )
            Row(
                Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(text = "Completed $finishPercent%")
                Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                    TextButton(onClick = back) {
                        Text(text = "Home")

                    }
                    Button(
                        onClick = onFinish,
                        colors = if (finishPercent == 100)
                            ButtonDefaults.buttonColors(containerColor = Color.Green)
                        else
                            ButtonDefaults.textButtonColors()
                    ) {
                        Text(text = "Submit")
                    }
                }
            }

        }
    }

    InstructionBottomSheet(
        show = showInstruct,
        instructionUiState = questions[state.currentPage].instructionUiState!!,
        generalPath = "",
        onDismissRequest = {showInstruct=false}
    )
    AllQuestionBottomSheet(
        show = show,
        chooses = chooses,
        onChooseClick = {
            show = false
            coroutineScope
                .launch {
                    state.animateScrollToPage(it)
                }
        },
        onDismissRequest = { show = false })

}

@Preview
@Composable
fun QuestionScreenPreview() {
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
    QuestionScreen(
        questions = questions,
        profileState = ProfileState()
    )
}


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
    show: Boolean,
    instructionUiState: InstructionUiState,
    generalPath: String,
    onDismissRequest: () -> Unit = {}
) {
    if (show) {
        ModalBottomSheet(onDismissRequest = onDismissRequest) {
            Column(
                Modifier.padding(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(text = "Instructions")

                if (instructionUiState.title != null) {
                    Text(text = instructionUiState.title!!)
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
