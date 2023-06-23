package com.mshdabiola.screen.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Kitesurfing
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
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
import com.mshdabiola.screen.MainViewModel
import com.mshdabiola.ui.QuestionScroll
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.TimeCounter
import com.mshdabiola.ui.com.mshdabiola.ui.AllQuestionBottomSheet
import com.mshdabiola.ui.com.mshdabiola.ui.InstructionBottomSheet
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun QuestionScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: MainViewModel
) {


    val questions = viewModel.questionsList.collectAsState()

    QuestionScreen(
        questions = questions.value,
        profileState = ProfileState(),
        back = onBack,
        onFinish = onFinish,
        onOption = viewModel::onOption
    )
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
internal fun QuestionScreen(
    questions: ImmutableList<QuestionUiState>,
    profileState: ProfileState,
    back: () -> Unit = {},
    onFinish: () -> Unit = {},
    onOption: (Int, Int) -> Unit = { _, _ -> }
) {

    var show by remember {
        mutableStateOf(false)
    }
    var instructionUiState by remember {
        mutableStateOf<InstructionUiState?>(null)
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
    val scrollState = rememberScrollState()


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

                },
                floatingActionButton = {
                    ExtendedFloatingActionButton(
                        onClick = onFinish,
                        containerColor = if (finishPercent == 100)
                            Color.Green
                        else
                            FloatingActionButtonDefaults.containerColor
                    ) {
                        Icon(imageVector = Icons.Default.Kitesurfing, contentDescription = "submit")
                        Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                        Text(text = "Submit: $finishPercent%")
                    }
                }
            )
        }

    ) { paddingValues ->
        Column(
            Modifier
                .padding(paddingValues)
                .padding(8.dp)
                .fillMaxSize()
                ,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            TimeCounter(
                modifier = Modifier.padding(top = 4.dp),
                currentTime = 400, total = 500
            )

            Spacer(modifier = Modifier.height(8.dp))

            HorizontalPager(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(state = scrollState)
                ,
                state = state,
                verticalAlignment = Alignment.Top
            ) { index ->
                QuestionUi(
                    number = (index + 1L),
                    questionUiState = questions[index],
                    generalPath = "",
                    title = "Waec 2015 Q4",
                    onInstruction = {
                        instructionUiState = questions[index].instructionUiState!!
                    },
                    onOptionClick = {
                        onOption(index, it)
                        if (state.canScrollForward) {
                            coroutineScope
                                .launch {
                                    state.animateScrollToPage(index + 1)
                                }
                        }
                    }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

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


        }
    }

    InstructionBottomSheet(
        instructionUiState = instructionUiState,
        generalPath = "",
        onDismissRequest = { instructionUiState = null }
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
