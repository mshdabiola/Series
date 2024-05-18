/*
 *abiola 2022
 */

package com.mshdabiola.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material.icons.filled.Kitesurfing
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedSuggestionChip
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChipDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.mvvn.collectAsStateWithLifecycleCommon
import com.mshdabiola.mvvn.semanticsCommon
import com.mshdabiola.ui.AllQuestionBottomSheet
import com.mshdabiola.ui.InstructionBottomSheet
import com.mshdabiola.ui.QuestionScroll
import com.mshdabiola.ui.QuestionUi
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.TimeCounter
import com.mshdabiola.ui.correct
import com.mshdabiola.ui.getSection
import com.mshdabiola.ui.onCorrect
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.MainState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun QuestionRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
    navigateToFinish: () -> Unit,
    viewModel: QuestionViewModel,

    ) {
    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon()

    QuestionScreen(
        mainStat = mainState.value,
        back = onBack,
        onFinish ={navigateToFinish()},
        onOption = viewModel::onOption,
        onTimeChanged = viewModel::onTimeChanged,
        changeIndex = viewModel::changeIndex,
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
)
@Composable
internal fun QuestionScreen(
    mainStat: MainState,
    back: () -> Unit = {},
    onFinish: () -> Unit = {},
    onNextTheory: (Int) -> Unit = {},
    onOption: (Int, Int, Int) -> Unit = { _, _, _ -> }, // paper,question,option
    onTimeChanged: (Long) -> Unit = {},
    changeIndex: (Int) -> Unit = {},
) {
    if (mainStat.questions.flatten().isEmpty()) {
        Text(text = "empty")
    } else {
        var show by remember {
            mutableStateOf(false)
        }
        var instructionUiState by remember {
            mutableStateOf<InstructionUiState?>(null)
        }
        val coroutineScope = rememberCoroutineScope()

        val finishPercent = remember(mainStat.choose) {
            val allChoose = mainStat
                .choose
                .flatten()

            (
                    (
                            allChoose.count {
                                it > -1
                            } / allChoose.size.toFloat()
                            ) * 100
                    ).toInt()
        }
        val states = getState(
            sizes = mainStat
                .questions
                .map { it.size }
                .toImmutableList(),
        )

        LaunchedEffect(key1 = mainStat.currentTime, block = {
            mainStat.currentExam?.let {
                if (mainStat.currentTime == mainStat.totalTime) {
                    onTimeChanged(mainStat.totalTime)
                    onFinish()
                }
            }
        })

        Scaffold(
            modifier = Modifier.semanticsCommon {},
            bottomBar = {
                BottomAppBar(
                    actions = {
                        IconButton(onClick = back) {
                            Icon(
                                imageVector = Icons.Default.ArrowBackIosNew,
                                contentDescription = "back",
                            )
                        }
                    },
                    floatingActionButton = {
                        ExtendedFloatingActionButton(
                            onClick = onFinish,
                            containerColor = if (finishPercent == 100) {
                                correct()
                            } else {
                                FloatingActionButtonDefaults.containerColor
                            },
                            contentColor = if (finishPercent == 100) {
                                onCorrect()
                            } else {
                                contentColorFor(backgroundColor = FloatingActionButtonDefaults.containerColor)
                            },
                        ) {
                            Icon(
                                imageVector = Icons.Default.Kitesurfing,
                                contentDescription = "submit",
                            )
                            Spacer(modifier = Modifier.width(ButtonDefaults.IconSpacing))
                            Text(text = "Submit: $finishPercent%")
                        }
                    },
                )
            },

            ) { paddingValues ->
            Column(
                Modifier
                    .padding(paddingValues)
                    .padding(8.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                TimeCounter(
                    modifier = Modifier.padding(top = 4.dp),
                    currentTime2 = mainStat.currentTime,
                    total = mainStat.totalTime,
                    onTimeChanged = onTimeChanged,
                )

                Spacer(modifier = Modifier.height(8.dp))

                // AnimatedContent(modifier = Modifier.fillMaxSize(), targetState = mainStat.currentPaper, label = "dd") { paperIndex ->
                ExamPaper(
                    questions = mainStat.questions[mainStat.currentSectionIndex],
                    state = states[mainStat.currentSectionIndex],
                    choose = mainStat.choose[mainStat.currentSectionIndex],
                    onNextTheory = onNextTheory,
                    onOption = { quIndex, optinId ->
                        onOption(
                            mainStat.currentSectionIndex,
                            quIndex,
                            optinId,
                        )
                    },
                    setInstructionUiState = { instructionUiState = it },
                )
                LazyRow(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(
                        16.dp,
                        Alignment.CenterHorizontally,
                    ),
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    item {
                        TextButton(onClick = { show = true }) {
                            Text("Show all questions")
                        }
                    }

                    if (mainStat.questions.size > 1) {
                        itemsIndexed(mainStat.sections) { index, section ->
                            ElevatedSuggestionChip(
                                onClick = { changeIndex(index) },
                                colors = if (section.isFinished) {
                                    SuggestionChipDefaults.elevatedSuggestionChipColors(
                                        containerColor = correct(),
                                        labelColor = onCorrect(),
                                    )
                                } else {
                                    SuggestionChipDefaults.elevatedSuggestionChipColors()
                                },
                                label = {
                                    Text(getSection()[section.stringRes])
                                },
                            )
                        }
                    }
                }

                // }
            }
        }

        InstructionBottomSheet(
            instructionUiState = instructionUiState,
            onDismissRequest = { instructionUiState = null },
        )
        AllQuestionBottomSheet(
            show = show,
            chooses = mainStat.choose[mainStat.currentSectionIndex],
            onChooseClick = {
                show = false
                onNextTheory(it)
                coroutineScope
                    .launch {
                        states[mainStat.currentSectionIndex].animateScrollToPage(it)
                    }
            },
            currentNumber = states[mainStat.currentSectionIndex].currentPage,
            onDismissRequest = { show = false },
        )
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun ColumnScope.ExamPaper(
    questions: ImmutableList<QuestionUiState>,
    state: PagerState,
    choose: ImmutableList<Int>,
    onNextTheory: (Int) -> Unit = {},
    setInstructionUiState: (InstructionUiState?) -> Unit = {},
    onOption: (Int, Int) -> Unit = { _, _ -> },
) {
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberScrollState()
    LaunchedEffect(key1 = state.currentPage) {
        try {
            val question = questions[state.currentPage]
            if (question.isTheory) {
                onOption(state.currentPage, 2)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    HorizontalPager(
        modifier = Modifier
            .weight(1f)
            .verticalScroll(state = scrollState),
        state = state,
        verticalAlignment = Alignment.Top,
        userScrollEnabled = false,
    ) { index ->

        QuestionUi(
            number = (index + 1L),
            questionUiState = questions[index],

            onInstruction = {
                setInstructionUiState(questions[index].instructionUiState!!)
            },
            selectedOption = choose.getOrNull(index) ?: -1,
            onOptionClick = {
                onOption(index, it)
                if (state.canScrollForward) {
                    coroutineScope
                        .launch {
                            state.animateScrollToPage(index + 1)
                            scrollState.scrollTo(0)
                        }
                }
            },
        )
    }

    Spacer(modifier = Modifier.height(8.dp))

    QuestionScroll(
        currentQuestion = state.currentPage,
        showPrev = state.canScrollBackward,
        showNext = state.canScrollForward,
        chooses = choose,
        onChooseClick = {
            onNextTheory(it)
            coroutineScope.launch {
                state.animateScrollToPage(it)
                scrollState.scrollTo(0)
            }
        },

        onNext = {
            coroutineScope.launch {
                onNextTheory(state.currentPage + 1)
                state.animateScrollToPage(state.currentPage + 1)
                scrollState.scrollTo(0)
            }
        },
        onPrev = {
            coroutineScope.launch {
                state.animateScrollToPage(state.currentPage - 1)
                scrollState.scrollTo(0)
            }
        },
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun getState(sizes: ImmutableList<Int>): ImmutableList<PagerState> {
    return sizes.map {
        rememberPagerState {
            it
        }
    }.toImmutableList()
}
//
//@Composable
//expect fun QuestionScreenPreview()
