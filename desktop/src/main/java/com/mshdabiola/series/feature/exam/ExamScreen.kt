package com.mshdabiola.series.feature.exam

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.instructionui.InstructionEditUi
import com.mshdabiola.ui.instructionui.InstructionUi
import com.mshdabiola.ui.questionui.QuestionEditUi
import com.mshdabiola.ui.questionui.QuestionUi
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    onBack: () -> Unit = {}
) {
    Scaffold(
        topBar = {
            TopAppBar(
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Default.ArrowBack, "back")
                    }
                },

                title = { Text("Exam Screen") })
        }
    ) { paddingValues ->

        val questions = viewModel.questions.collectAsState()
        val instructionUiStates = viewModel.instructions.collectAsState()
        val pagerState = rememberPagerState()
        val coroutineScope = rememberCoroutineScope()
        Column(Modifier.fillMaxSize().padding(paddingValues).padding(horizontal = 16.dp)) {
            TabRow(
                selectedTabIndex = pagerState.currentPage,
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(0)
                        }
                    },
                    text = { Text("Question") }
                )
                Tab(
                    selected = pagerState.currentPage == 1,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(1)
                        }
                    },
                    text = { Text("Instruction") }
                )
                Tab(
                    selected = pagerState.currentPage == 2,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(2)
                        }
                    },
                    text = { Text("Topic") }
                )

            }
            HorizontalPager(
                modifier = Modifier.padding(top = 8.dp).weight(1f),
                pageCount = 3,
                state = pagerState
            ) {
                when (it) {
                    0 ->
                        ExamContent(
                            modifier = Modifier,
                            questionUiState = viewModel.question.value,
                            questions = questions.value,
                            instructIdError = viewModel.instructIdError.value,
                            addUp = viewModel::addUP,
                            addBottom = viewModel::addDown,
                            moveUp = viewModel::moveUP,
                            moveDown = viewModel::moveDown,
                            edit = viewModel::edit,
                            delete = viewModel::delete,
                            changeType = viewModel::changeType,
                            onTextChange = viewModel::onTextChange,
                            onAddQuestion = viewModel::onAddQuestion,
                            onAddOption = viewModel::addOption,
                            onDeleteQuestion = viewModel::onDeleteQuestion,
                            onUpdateQuestion = viewModel::onUpdateQuestion,
                            onMoveDownQuestion = viewModel::onMoveDownQuestion,
                            onMoveUpQuestion = viewModel::onMoveUpQuestion,
                            onAnswer = viewModel::onAnswerClick,
                            instructionIdChange = viewModel::onInstructionIdChange
                        )

                    1 ->
                        InstructionContent(
                            instructionUiState = viewModel.instructionUiState.value,
                            instructionUiStates = instructionUiStates.value,
                            onTitleChange = viewModel::instructionTitleChange,
                            addUp = viewModel::addUpInstruction,
                            addBottom = viewModel::addDownInstruction,
                            delete = viewModel::deleteInstruction,
                            moveUp = viewModel::moveUpInstruction,
                            moveDown = viewModel::moveDownInstruction,
                            edit = viewModel::editInstruction,
                            changeType = viewModel::changeTypeInstruction,
                            onTextChange = viewModel::onTextChangeInstruction,
                            onAddInstruction = viewModel::onAddInstruction,
                            onDeleteInstruction = viewModel::onDeleteInstruction,
                            onUpdateInstruction = viewModel::onUpdateInstruction
                        )

                    else ->
                        TopicContent()
                }

            }
        }


    }

}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class,
    ExperimentalMaterial3Api::class
)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    instructIdError:Boolean,
    questions: ImmutableList<QuestionUiState>,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    edit: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Type) -> Unit = { _, _, _ -> },
    onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> },
    onAddQuestion: () -> Unit = {},
    onAddOption: () -> Unit = {},
    onUpdateQuestion: (Long) -> Unit = {},
    onDeleteQuestion: (Long) -> Unit = {},
    onMoveUpQuestion: (Long) -> Unit = {},
    onMoveDownQuestion: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
    instructionIdChange:(String)->Unit={}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(questions, key = { it.id }) {
                    QuestionUi(
                        questionUiState = it,
                        onDelete = onDeleteQuestion,
                        onUpdate = onUpdateQuestion,
                        onMoveDown = onMoveDownQuestion,
                        onMoveUp = onMoveUpQuestion,
                        onAnswer = onAnswer
                    )
                }
            }

        }
        second {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                Row(Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        modifier=Modifier.weight(0.5f),
                        value = questionUiState.instructionId?.toString() ?:"",
                        onValueChange = instructionIdChange,
                        isError = instructIdError,
                        label = {Text("Instruction id")}
                    )

                    OutlinedTextField(
                        modifier=Modifier.weight(0.5f),
                        value = questionUiState.topicId?.toString() ?: "",
                        onValueChange = {},
                        label = { Text("Topic")}
                    )
                }
                QuestionEditUi(
                    questionUiState = questionUiState,
                    addUp = addUp,
                    addBottom = addBottom,
                    moveDown = moveDown,
                    moveUp = moveUp,
                    delete = delete,
                    edit = edit,
                    changeType = changeType,
                    onTextChange = onTextChange
                )
                Row(Modifier.fillMaxSize()) {
                    IconButton(onClick = onAddOption) {
                        Icon(Icons.Default.Add, "")
                    }
                    Spacer(Modifier.weight(1f))
                    Button(modifier = Modifier, onClick = onAddQuestion) {
                        Icon(Icons.Default.Add, "add")
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Add Question")
                    }
                }


            }


        }
    }

}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
@Composable
fun TopicContent(
    modifier: Modifier = Modifier
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            Text("Topic")

        }
        second {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {


            }
        }
    }

}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
@Composable
fun InstructionContent(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    instructionUiStates: ImmutableList<InstructionUiState>,
    onTitleChange: (String) -> Unit = {},
    addUp: (Int) -> Unit = { _ -> },
    addBottom: (Int) -> Unit = { _ -> },
    delete: (Int) -> Unit = { _ -> },
    moveUp: (Int) -> Unit = { _ -> },
    moveDown: (Int) -> Unit = { _ -> },
    edit: (Int) -> Unit = { _ -> },
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onTextChange: (Int, String) -> Unit = { _, _ -> },
    onAddInstruction: () -> Unit = {},
    onDeleteInstruction: (Long) -> Unit = {},
    onUpdateInstruction: (Long) -> Unit = {}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(Modifier.fillMaxSize()) {
                items(
                    items = instructionUiStates,
                    key = { it.id }
                ) {
                    InstructionUi(
                        instructionUiState = it,
                        onUpdate = onUpdateInstruction,
                        onDelete = onDeleteInstruction
                    )

                }
            }

        }
        second {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                InstructionEditUi(
                    modifier = Modifier.fillMaxWidth(),
                    instructionUiState = instructionUiState,
                    onTitleChange = onTitleChange,
                    addUp = addUp,
                    addBottom = addBottom,
                    delete = delete,
                    moveUp = moveUp,
                    moveDown = moveDown,
                    edit = edit,
                    changeType = changeType,
                    onTextChange = onTextChange
                )
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onAddInstruction,
                        enabled = instructionUiState.content.first().content.isNotBlank()
                    ) {
                        Text("Add Instruction")
                    }
                }

            }
        }
    }

}
