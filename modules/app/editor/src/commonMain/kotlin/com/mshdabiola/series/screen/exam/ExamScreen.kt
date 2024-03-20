package com.mshdabiola.series.screen.exam

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.series.CommonScreen2
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

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExamScreen(
    viewModel: ExamViewModel,
    onBack: () -> Unit = {},
    windowSizeClass: WindowSizeClass,
) {
    val action: @Composable RowScope.() -> Unit = {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "back")
        }
    }
    var show by remember { mutableStateOf(false) }
    val onDismiss = { show = false }

    Scaffold(
        topBar = {
            if (windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded) {
                TopAppBar(
                    navigationIcon = {
                        IconButton(onClick = onBack) {
                            Icon(Icons.Default.ArrowBack, "back")
                        }
                    },

                    title = { Text("Exam Screen") },
                )
            }
        },
        bottomBar = {
            if (windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded) {
                BottomAppBar(floatingActionButton = {
                    ExtendedFloatingActionButton(onClick = {
                        show = true
                    }) {
                        Icon(Icons.Default.Add, "add")
                        Spacer(Modifier.width(8.dp))
                        Text("Add")
                    }
                }, actions = action)
            }
        },
    ) { paddingValues ->

        val questions = viewModel.questions
        val instructionUiStates = viewModel.instructions
        val topicUiStates = viewModel.topicUiStates
        var state by remember {
            mutableStateOf(0)
        }
        val coroutineScope = rememberCoroutineScope()
        Column(Modifier.padding(paddingValues).fillMaxSize()) {
            TabRow(
                selectedTabIndex = state,
                modifier = Modifier,
            ) {
                Tab(
                    selected = state == 0,
                    onClick = {
                        state = 0
                    },
                    text = { Text("Question") },
                )
                Tab(
                    selected = state == 1,
                    onClick = {
                        state = 1
                    },
                    text = { Text("Instruction") },
                )
                Tab(
                    selected = state == 2,
                    onClick = {
                        state = 2
                    },
                    text = { Text("Topic") },
                )
            }
            Box(
                modifier = Modifier.padding(top = 8.dp).weight(1f),
                // pageCount = 3,

            ) {
                when (state) {
                    0 ->
                        ExamContent(
                            modifier = Modifier,
                            questionUiState = viewModel.question.value,
                            questions = questions.value,
                            instructIdError = viewModel.instructIdError.value,
                            topicUiStates = topicUiStates.value,
                            examInputUiState = viewModel.examInputUiState.value,
                            windowSizeClass = windowSizeClass,
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
                            onAddAnswer = viewModel::onAddAnswer,
                            isTheory = viewModel::isTheory,
                            onDeleteQuestion = viewModel::onDeleteQuestion,
                            onUpdateQuestion = viewModel::onUpdateQuestion,
                            onMoveDownQuestion = viewModel::onMoveDownQuestion,
                            onMoveUpQuestion = viewModel::onMoveUpQuestion,
                            onAnswer = viewModel::onAnswerClick,
                            instructionIdChange = viewModel::onInstructionIdChange,
                            onTopicSelect = viewModel::onTopicSelect,
                            onAddExamInUiState = viewModel::onAddExamFromInput,
                            onExamInputChange = viewModel::onExamInputChanged,
                            show = show,
                            onDismiss = onDismiss,
                        )

                    1 ->
                        InstructionContent(
                            instructionUiState = viewModel.instructionUiState.value,
                            instructionUiStates = instructionUiStates.value,
                            instruInputUiState = viewModel.instruInputUiState.value,
                            windowSizeClass = windowSizeClass,
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
                            onInstruInputChange = viewModel::onInstuInputChanged,
                            show = show,
                            onDismiss = onDismiss,
                        )

                    else ->
                        TopicContent(
                            topicUiState = viewModel.topicUiState.value,
                            topicUiStates = topicUiStates.value,
                            topicInputUiState = viewModel.topicInputUiState.value,
                            windowSizeClass = windowSizeClass,
                            onAddTopic = viewModel::onAddTopic,
                            onTopicChange = viewModel::onTopicChange,
                            onDelete = viewModel::onDeleteTopic,
                            onUpdate = viewModel::onUpdateTopic,
                            onAddTopicInputUiState = viewModel::onAddTopicFromInput,
                            onTopicInputChange = viewModel::onTopicInputChanged,
                            show = show,
                            onDismiss = onDismiss,
                        )
                }
            }
        }
    }
}

@OptIn(
    ExperimentalFoundationApi::class,
    ExperimentalLayoutApi::class,
)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    instructIdError: Boolean,
    questions: ImmutableList<QuestionUiState>,
    topicUiStates: ImmutableList<TopicUiState>,
    examInputUiState: ExamInputUiState,
    windowSizeClass: WindowSizeClass,
    show: Boolean = false,
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
    onAddAnswer: (Boolean) -> Unit = {},
    isTheory: (Boolean) -> Unit = {},
    onUpdateQuestion: (Long) -> Unit = {},
    onDeleteQuestion: (Long) -> Unit = {},
    onMoveUpQuestion: (Long) -> Unit = {},
    onMoveDownQuestion: (Long) -> Unit = {},
    onAnswer: (Long, Long) -> Unit = { _, _ -> },
    instructionIdChange: (String) -> Unit = {},
    onTopicSelect: (Long) -> Unit = {},
    onAddExamInUiState: () -> Unit = {},
    onExamInputChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},

) {
    var showTopiDropdown by remember { mutableStateOf(false) }
    var showConvert by remember { mutableStateOf(false) }

    var fillIt =
        rememberUpdatedState(windowSizeClass.widthSizeClass != WindowWidthSizeClass.Expanded)

    CommonScreen2(
        windowSizeClass,
        firstScreen = {
            LazyColumn(
                it.padding(8.dp).fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(questions, key = { it.id }) {
                    QuestionUi(
                        questionUiState = it,
                        onDelete = onDeleteQuestion,
                        onUpdate = onUpdateQuestion,
                        onMoveDown = onMoveDownQuestion,
                        onMoveUp = onMoveUpQuestion,
                        onAnswer = onAnswer,
                    )
                }
            }
        },
        secondScreen = {
            Column(
                modifier = Modifier.padding(8.dp).fillMaxWidth()
                    .verticalScroll(rememberScrollState()),
            ) {
                Row(
                    Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    OutlinedTextField(
                        modifier = Modifier.weight(0.5f),
                        value = questionUiState.instructionUiState?.id?.toString() ?: "",
                        onValueChange = instructionIdChange,
                        isError = instructIdError,
                        label = { Text("Instruction id") },
                    )

                    Box(modifier = Modifier.weight(0.5f)) {
                        OutlinedTextField(
                            readOnly = true,
                            maxLines = 1,
                            value = questionUiState.topicUiState?.name ?: "",
                            onValueChange = {},
                            label = { Text("Topic id") },
                            trailingIcon = {
                                IconButton(onClick = { showTopiDropdown = true }) {
                                    Icon(Icons.Default.ArrowDropDown, "drop")
                                }
                            },
                        )
                        DropdownMenu(
                            expanded = showTopiDropdown,
                            onDismissRequest = { showTopiDropdown = false },
                        ) {
                            topicUiStates.forEach {
                                DropdownMenuItem(onClick = {
                                    onTopicSelect(it.id)
                                    showTopiDropdown = false
                                }, text = {
                                    Text(
                                        it.name,
                                        maxLines = 1,
                                        modifier = Modifier
                                            .basicMarquee(iterations = 2),
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
                    onTextChange = onTextChange,
                    fillIt = fillIt.value,
                )
                FlowRow(
                    Modifier.fillMaxSize(),
                    verticalArrangement = Arrangement.spacedBy(4.dp, Alignment.CenterVertically),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    maxItemsInEachRow = 2,
                ) {
                    SuggestionChip(
                        onClick = onAddOption,
                        label = { Text("Add Option") },
                    )

                    SuggestionChip(
                        onClick = { onAddAnswer(questionUiState.answer == null) },
                        label = {
                            Text(
                                if (questionUiState.answer == null) "Add Answers" else "Remove answer",
                            )
                        },
                    )
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text("Is Theory")
                        Spacer(Modifier.width(2.dp))
                        Switch(
                            checked = questionUiState.isTheory,
                            onCheckedChange = { isTheory(it) },
                            enabled = questionUiState.id < 0,
                        )
                    }

                    Button(modifier = Modifier, onClick = onAddQuestion) {
                        Icon(Icons.Default.Add, "add")
                        Spacer(Modifier.width(ButtonDefaults.IconSpacing))
                        Text("Add Question")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .clickable(onClick = { showConvert = !showConvert })
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down",
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = examInputUiState.content,
                            onValueChange = onExamInputChange,
                            isError = examInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("*q* question *o* option *t* type 0or1 *a* answer")
                            Button(
                                modifier = Modifier,
                                onClick = onAddExamInUiState,
                            ) {
                                Text("Convert to Exam")
                            }
                        }
                    }
                }
                Spacer(Modifier.height(16.dp))
                TemplateUi()
            }
        },
        show,
        onDismiss,
    )
}

@OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalFoundationApi::class,
)
@Composable
fun TopicContent(
    modifier: Modifier = Modifier,
    topicUiState: TopicUiState,
    topicInputUiState: TopicInputUiState,
    topicUiStates: ImmutableList<TopicUiState>,
    windowSizeClass: WindowSizeClass,
    show: Boolean = false,
    onTopicChange: (String) -> Unit = {},
    onAddTopic: () -> Unit = {},
    onDelete: (Long) -> Unit = {},
    onUpdate: (Long) -> Unit = {},
    onAddTopicInputUiState: () -> Unit = {},
    onTopicInputChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var showConvert by remember { mutableStateOf(false) }

    CommonScreen2(
        windowSizeClass,
        firstScreen = {
            LazyColumn(it.fillMaxSize()) {
                items(items = topicUiStates, key = { it.id }) {
                    TopicUi(
                        topicUiState = it,
                        onDelete = onDelete,
                        onUpdate = onUpdate,
                    )
                }
            }
        },
        secondScreen = {
            Column(
                modifier = Modifier.fillMaxWidth().imePadding()
                    .verticalScroll(rememberScrollState()),
            ) {
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
                    maxLines = 1,
                )
                Spacer(Modifier.height(4.dp))
                Button(onClick = onAddTopic) {
                    Text(if (topicUiState.id < 0) "Add topic" else "Update topic")
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .clickable(onClick = { showConvert = !showConvert })
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down",
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = topicInputUiState.content,
                            onValueChange = onTopicInputChange,
                            isError = topicInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("* topic title")
                            Button(
                                modifier = Modifier,
                                onClick = onAddTopicInputUiState,
                            ) {
                                Text("Convert to topic")
                            }
                        }
                    }
                }
            }
        },
        show,
        onDismiss,
    )
}

@OptIn(
    ExperimentalFoundationApi::class,
)
@Composable
fun InstructionContent(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    instructionUiStates: ImmutableList<InstructionUiState>,
    instruInputUiState: InstruInputUiState,
    windowSizeClass: WindowSizeClass,
    show: Boolean = false,
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
    onInstruInputChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var showConvert by remember { mutableStateOf(false) }

    CommonScreen2(
        windowSizeClass,
        firstScreen = {
            LazyColumn(it.fillMaxSize()) {
                items(
                    items = instructionUiStates,
                    key = { it.id },
                ) {
                    InstructionUi(
                        instructionUiState = it,
                        onUpdate = onUpdateInstruction,
                        onDelete = onDeleteInstruction,
                    )
                }
            }
        },
        secondScreen = {
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
                    onTextChange = onTextChange,
                )
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onAddInstruction,
                        enabled = instructionUiState.content.first().content.isNotBlank(),
                    ) {
                        Text("Add Instruction")
                    }
                }
                Spacer(Modifier.height(16.dp))
                Row(
                    Modifier
                        .clickable(onClick = { showConvert = !showConvert })
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text("Convert text to exams")
                    IconButton(modifier = Modifier, onClick = { showConvert = !showConvert }) {
                        Icon(
                            if (!showConvert) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                            "down",
                        )
                    }
                }

                if (showConvert) {
                    Column {
                        OutlinedTextField(
                            value = instruInputUiState.content,
                            onValueChange = onInstruInputChange,
                            isError = instruInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("* instruction title")
                            Button(
                                modifier = Modifier,
                                onClick = onAddInstruInputUiState,
                            ) {
                                Text("Convert to Instruction")
                            }
                        }
                    }
                }
            }
        },
        show,
        onDismiss,
    )
}
