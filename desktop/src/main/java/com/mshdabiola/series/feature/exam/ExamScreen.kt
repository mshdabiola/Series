package com.mshdabiola.series.feature.exam

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.foundation.onClick
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.TemplateUi
import com.mshdabiola.ui.instructionui.InstructionEditUi
import com.mshdabiola.ui.instructionui.InstructionUi
import com.mshdabiola.ui.questionui.QuestionEditUi
import com.mshdabiola.ui.questionui.QuestionUi
import com.mshdabiola.ui.state.ExamInputUiState
import com.mshdabiola.ui.state.InstruInputUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicInputUiState
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.topicui.TopicUi
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
        val topicUiStates = viewModel.topicUiStates.collectAsState()
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
                            topicUiStates = topicUiStates.value,
                            examInputUiState = viewModel.examInputUiState.value,
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
                            instructionIdChange = viewModel::onInstructionIdChange,
                            onTopicSelect = viewModel::onTopicSelect,
                            onAddExamInUiState = viewModel::onAddExamFromInput,
                            onExamInputChange = viewModel::onExamInputChanged
                        )

                    1 ->
                        InstructionContent(
                            instructionUiState = viewModel.instructionUiState.value,
                            instructionUiStates = instructionUiStates.value,
                            instruInputUiState = viewModel.instruInputUiState.value,
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
                            onUpdateInstruction = viewModel::onUpdateInstruction,
                            onAddInstruInputUiState = viewModel::onAddInstruTopicFromInput,
                            onInstruInputChange = viewModel::onInstuInputChanged
                        )

                    else ->
                        TopicContent(
                            topicUiState = viewModel.topicUiState.value,
                            topicUiStates = topicUiStates.value,
                            topicInputUiState = viewModel.topicInputUiState.value,
                            onAddTopic = viewModel::onAddTopic,
                            onTopicChange = viewModel::onTopicChange,
                            onDelete = viewModel::onDeleteTopic,
                            onUpdate = viewModel::onUpdateTopic,
                            onAddTopicInputUiState = viewModel::onAddTopicFromInput,
                            onTopicInputChange = viewModel::onTopicInputChanged
                        )
                }

            }
        }


    }

}

@OptIn(
    ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class,
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    instructIdError: Boolean,
    questions: ImmutableList<QuestionUiState>,
    topicUiStates: ImmutableList<TopicUiState>,
    examInputUiState: ExamInputUiState,
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
    instructionIdChange: (String) -> Unit = {},
    onTopicSelect: (Long) -> Unit = {},
    onAddExamInUiState: () -> Unit = {},
    onExamInputChange: (String) -> Unit = {}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    var showTopiDropdown by remember { mutableStateOf(false) }
    var showConvert by remember { mutableStateOf(false) }

    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(
                Modifier.padding(8.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
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
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState())
            ) {

                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {

                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = questionUiState.instructionUiState?.id?.toString() ?: "",
                        onValueChange = instructionIdChange,
                        isError = instructIdError,
                        label = { Text("Instruction id") }
                    )

                    Box(modifier = Modifier.weight(0.5f)) {

                        OutlinedTextField(
                            readOnly = true,
                            maxLines = 1,
                            value = questionUiState.topicUiState?.name ?: "",
                            onValueChange = {},
                            label = { Text("Topic") },
                            trailingIcon = {
                                IconButton(onClick = { showTopiDropdown = true }) {
                                    Icon(Icons.Default.ArrowDropDown, "drop")
                                }
                            }
                        )
                        DropdownMenu(
                            expanded = showTopiDropdown,
                            onDismissRequest = { showTopiDropdown = false }) {
                            topicUiStates.forEach {
                                DropdownMenuItem(onClick = {
                                    onTopicSelect(it.id)
                                    showTopiDropdown = false
                                }, text = {
                                    Text(
                                        it.name, maxLines = 1, modifier = Modifier
                                            .basicMarquee(iterations = 2)
                                    )
                                })
                            }
                        }
                    }
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
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .onClick { showConvert = !showConvert }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        androidx.compose.material.Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down"
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = examInputUiState.content,
                            onValueChange = onExamInputChange,
                            isError = examInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = onAddExamInUiState
                        ) {
                            Text("Convert to Exam")
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                TemplateUi()


            }


        }
    }

}

@OptIn(
    ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class,
    ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class
)
@Composable
fun TopicContent(
    modifier: Modifier = Modifier,
    topicUiState: TopicUiState,
    topicInputUiState: TopicInputUiState,
    topicUiStates: ImmutableList<TopicUiState>,
    onTopicChange: (String) -> Unit = {},
    onAddTopic: () -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    onAddTopicInputUiState: () -> Unit = {},
    onTopicInputChange: (String) -> Unit = {}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.7f)
    var showConvert by remember { mutableStateOf(false) }
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(Modifier.fillMaxSize()) {
                items(items = topicUiStates, key = { it.id }) {
                    TopicUi(
                        topicUiState = it,
                        onDelete = onDelete, onUpdate = onUpdate
                    )

                }
            }

        }
        second {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {

                val focusRequester = remember {
                    FocusRequester()
                }
                LaunchedEffect(topicUiState.focus) {
                    if (topicUiState.focus) {
                        focusRequester.requestFocus()
                    }
                }
                TextField(
                    modifier = Modifier
                        .focusRequester(focusRequester)
                        .fillMaxWidth(),
                    value = topicUiState.name,
                    onValueChange = onTopicChange,
                    label = { Text("Topic") },
                    maxLines = 1
                )
                Spacer(Modifier.height(4.dp))
                Button(onClick = onAddTopic) {
                    Text(if (topicUiState.id < 0) "Add topic" else "Update topic")
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .onClick { showConvert = !showConvert }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        androidx.compose.material.Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down"
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = topicInputUiState.content,
                            onValueChange = onTopicInputChange,
                            isError = topicInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = onAddTopicInputUiState
                        ) {
                            Text("Convert to topic")
                        }
                    }
                }


            }
        }
    }

}

@OptIn(
    ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class,
    ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class
)
@Composable
fun InstructionContent(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    instructionUiStates: ImmutableList<InstructionUiState>,
    instruInputUiState: InstruInputUiState,
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
    onUpdateInstruction: (Long) -> Unit = {},
    onAddInstruInputUiState: () -> Unit = {},
    onInstruInputChange: (String) -> Unit = {}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    var showConvert by remember { mutableStateOf(false) }
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
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .onClick { showConvert = !showConvert }
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        androidx.compose.material.Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down"
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = instruInputUiState.content,
                            onValueChange = onInstruInputChange,
                            isError = instruInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp)
                        )
                        Spacer(Modifier.height(4.dp))
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            onClick = onAddInstruInputUiState
                        ) {
                            Text("Convert to Instruction")
                        }
                    }
                }

            }
        }
    }

}
