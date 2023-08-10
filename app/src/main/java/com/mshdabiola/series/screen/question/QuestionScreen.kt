package com.mshdabiola.series.screen.question

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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import com.mshdabiola.series.screen.MainViewModel
import com.mshdabiola.series.screen.main.MainState
import com.mshdabiola.ui.QuestionScroll
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.TimeCounter
import com.mshdabiola.ui.com.mshdabiola.ui.AllQuestionBottomSheet
import com.mshdabiola.ui.com.mshdabiola.ui.InstructionBottomSheet
import com.mshdabiola.ui.correct
import com.mshdabiola.ui.onCorrect
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.util.FileManager
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

@Composable
internal fun QuestionScreen(
    onBack: () -> Unit,
    onFinish: () -> Unit,
    viewModel: MainViewModel
) {


    val questions = viewModel.objQuestionsList.collectAsState()
    val mainState = viewModel.mainState.collectAsStateWithLifecycle()


    QuestionScreen(
        questions = questions.value ?: emptyList<QuestionUiState>().toImmutableList(),
        mainState = mainState.value,
        back = onBack,
        onFinish = {
            onFinish()
            viewModel.onSubmit()
        },
        onOption = viewModel::onOption,
        getGeneralPath = viewModel::getGeneraPath,
        onTimeChanged = viewModel::onTimeChanged
    )
}

@OptIn(
    ExperimentalComposeUiApi::class,
    ExperimentalFoundationApi::class
)
@Composable
internal fun QuestionScreen(
    questions: ImmutableList<QuestionUiState>,
    mainState: MainState,
    back: () -> Unit = {},
    onFinish: () -> Unit = {},
    onOption: (Int, Int) -> Unit = { _, _ -> },
    getGeneralPath: (FileManager.ImageType, Long) -> String = { _, _ -> "" },
    onTimeChanged: (Long) -> Unit = {}
) {
    if (questions.isEmpty()) {
        Text(text = "empty")
    } else {

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

        val finishPercent = remember(mainState.choose) {
            val choose = mainState.choose
            ((choose.count {
                it > -1
            } / choose.size.toFloat()) * 100).toInt()
        }
        val scrollState = rememberScrollState()


        LaunchedEffect(key1 = mainState.currentExam?.currentTime, block = {
            mainState.currentExam?.let {
                if (it.currentTime == it.totalTime) {
                    onFinish()
                }
            }

        })

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
                                MaterialTheme.correct()
                            else
                                FloatingActionButtonDefaults.containerColor,
                            contentColor = if (finishPercent == 100)
                                MaterialTheme.onCorrect()
                            else
                                contentColorFor(backgroundColor = FloatingActionButtonDefaults.containerColor)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Kitesurfing,
                                contentDescription = "submit"
                            )
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
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                TimeCounter(
                    modifier = Modifier.padding(top = 4.dp),
                    currentTime2 = mainState.currentExam?.currentTime ?: 0,
                    total = mainState.currentExam?.totalTime ?: 8,
                    onTimeChanged = onTimeChanged
                )

                Spacer(modifier = Modifier.height(8.dp))

                HorizontalPager(
                    modifier = Modifier
                        .weight(1f)
                        .verticalScroll(state = scrollState),
                    state = state,
                    verticalAlignment = Alignment.Top,
                    userScrollEnabled = false
                ) { index ->
                    QuestionUi(
                        number = (index + 1L),
                        questionUiState = questions[index],
                        generalPath = getGeneralPath(
                            FileManager.ImageType.QUESTION,
                            questions[index].examId
                        ),

                        onInstruction = {
                            instructionUiState = questions[index].instructionUiState!!
                        },
                        selectedOption = mainState.choose.getOrNull(index) ?: -1,
                        onOptionClick = {
                            onOption(index, it)
                            if (state.canScrollForward) {
                                coroutineScope
                                    .launch {
                                        state.animateScrollToPage(index + 1)
                                        scrollState.scrollTo(0)
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
                    chooses = mainState.choose,
                    onChooseClick = {
                        coroutineScope.launch {
                            state.animateScrollToPage(it)
                            scrollState.scrollTo(0)
                        }
                    },

                    onNext = {
                        coroutineScope.launch {
                            state.animateScrollToPage(state.currentPage + 1)
                            scrollState.scrollTo(0)
                        }
                    },
                    onPrev = {
                        coroutineScope.launch {
                            state.animateScrollToPage(state.currentPage - 1)
                            scrollState.scrollTo(0)
                        }
                    }
                )
                TextButton(onClick = { show = true }) {
                    Text("Show all questions")
                }


            }
        }

        InstructionBottomSheet(
            instructionUiState = instructionUiState,
            generalPath = getGeneralPath(
                FileManager.ImageType.INSTRUCTION,
                instructionUiState?.examId ?: 0
            ),
            onDismissRequest = { instructionUiState = null }
        )
        AllQuestionBottomSheet(
            show = show,
            chooses = mainState.choose,
            onChooseClick = {
                show = false
                coroutineScope
                    .launch {
                        state.animateScrollToPage(it)
                    }
            },
            currentNumber = state.currentPage,
            onDismissRequest = { show = false })


    }
}

@Preview
@Composable
fun QuestionScreenPreview() {
    val questions =
        listOf(
            QuestionUiState(
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
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
                        id = 2, nos = 2, content = listOf(
                            ItemUiState(
                                content = "Isabelle"
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
                        id = 4, nos = 4, content = listOf(
                            ItemUiState(
                                content = "Isabelle",
                            )
                        ).toImmutableList(),
                        isAnswer = false
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
        mainState = MainState(listOfAllExams = emptyList<ExamUiState>().toImmutableList())
    )
}
