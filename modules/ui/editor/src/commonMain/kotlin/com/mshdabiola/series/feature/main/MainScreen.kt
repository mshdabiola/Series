package com.mshdabiola.series.feature.main

//import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Deselect
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.mshdabiola.designsystem.theme.SeriesAppTheme
import com.mshdabiola.series.CommonScreen
import com.mshdabiola.ui.examui.ExamUi
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import java.io.File
import javax.swing.JFileChooser


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    windowSizeClass: WindowSizeClass,
    viewModel: MainViewModel,
    onExamClick: (Long, Long) -> Unit = { _, _ -> }
) {
    // var show by remember { mutableStateOf(false) }

    var showDrop by remember { mutableStateOf(false) }
    val subjects = viewModel.subjects.collectAsState()
    val currentSubjectIndex = viewModel.currentSubjectId.collectAsState().value
    var showDialog by remember {
        mutableStateOf(false)
    }
    MainDialog(showDialog, viewModel::onExport) { showDialog = false }
//    DirtoryUi(show, onDismiss = { show = false }, {
//        it?.let {
//            viewModel.onExport(it.path)
//        }
//    }
    //)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Main Screen") },
                actions = {
                    Box {
                        IconButton(
                            onClick = { showDrop = true },
                            //enabled = currentSubjectIndex > -1
                        ) {
                            Icon(Icons.Default.MoreVert, "more")
                        }
                        if (viewModel.isSelectMode.value) {
                            DropdownMenu(
                                expanded = showDrop,
                                onDismissRequest = { showDrop = false }) {


                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.SelectAll,
                                            "select All"
                                        )
                                    },
                                    text = { Text("Select All") },
                                    onClick = {
                                        viewModel.selectAll()
                                    })

                                if (currentSubjectIndex > -1) {

                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Subject,
                                                "select all subject"
                                            )
                                        },
                                        text = { Text("Select Current Subject") },
                                        onClick = {
                                            viewModel.selectAllSubject()
                                        })
                                }
                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.Deselect,
                                            "deselect"
                                        )
                                    },
                                    text = { Text("DeSelect All") },
                                    onClick = {
                                        viewModel.deselectAll()
                                        showDrop = false

                                    })

                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.SaveAs,
                                            "save"
                                        )
                                    },
                                    text = { Text("Export Selected") },
                                    onClick = {
                                        // onDelete(examUiState.id)
                                        showDialog = true
                                        showDrop = false
                                    })

                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Rounded.Delete,
                                            "delete"
                                        )
                                    },
                                    text = { Text("Delete selected") },
                                    onClick = {
                                        viewModel.deleteSelected()
                                        showDrop = false

                                    })

                            }

                        } else {

                            DropdownMenu(
                                expanded = showDrop,
                                onDismissRequest = { showDrop = false }) {

                                if (currentSubjectIndex > -1) {
                                    DropdownMenuItem(
                                        leadingIcon = {
                                            Icon(
                                                Icons.Default.Update,
                                                "update"
                                            )
                                        },
                                        text = { Text("Update") },
                                        onClick = {
                                            viewModel.updateSubject(currentSubjectIndex)
                                            showDrop = false
                                        })
                                }

                                DropdownMenuItem(
                                    leadingIcon = {
                                        Icon(
                                            Icons.Default.HdrOnSelect,
                                            "select mode"
                                        )
                                    },
                                    text = { Text("Select mode") },
                                    onClick = {
                                        viewModel.toggleSelectMode()
                                        showDrop = false
                                    })

//                            DropdownMenuItem(
//                                leadingIcon = {
//                                    androidx.compose.material.Icon(
//                                        Icons.Default.Delete,
//                                        "Delete"
//                                    )
//                                },
//                                text = { Text("Delete") },
//                                onClick = {
//
//                                })


                            }

                        }
                    }
                }
            )
        }
    ) { paddingValues ->
        if (windowSizeClass.widthSizeClass== WindowWidthSizeClass.Compact){
            ModalNavigationDrawer(
                modifier = Modifier.padding(paddingValues),
                drawerContent = {
                    LazyColumn(modifier = Modifier.width(200.dp)) {
                        item {
                            NavigationDrawerItem(
                                label = { Text("All Examination") },
                                onClick = { viewModel.onSubject(-1) },
                                selected = currentSubjectIndex == -1L
                            )
                        }
                        items(subjects.value, key = { it.id }) {
                            NavigationDrawerItem(
                                label = { Text(it.name) },
                                onClick = { viewModel.onSubject(it.id) },
                                selected = currentSubjectIndex == it.id
                            )
                        }

                    }
                },
                content = {

                    MainContent(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        exams = viewModel.examUiStates.collectAsState().value,
                        examYearError = viewModel.dateError.value,
                        examUiState = viewModel.exam.value,
                        subjectUiState = viewModel.subject.value,
                        isSelectMode = viewModel.isSelectMode.value,
                        windowSizeClass = windowSizeClass,
                        subjects = subjects.value,
                        addExam = viewModel::addExam,
                        addSubject = viewModel::addSubject,
                        onExamClick = onExamClick,
                        toggleSelect = viewModel::toggleSelect,
                        onSubjectIdChange = viewModel::onSubjectIdChange,
                        onExamYearChange = viewModel::onExamYearContentChange,
                        onExamDurationChange = viewModel::onExamDurationContentChange,
                        onSubjectNameChange = viewModel::onSubjectContentChange,
                        onDeleteSubject = viewModel::onDeleteExam,
                        onUpdateSubject = viewModel::onUpdateExam,

                        )
                })
        }else{
            PermanentNavigationDrawer(
                modifier = Modifier.padding(paddingValues),
                drawerContent = {
                    LazyColumn(modifier = Modifier.width(200.dp)) {
                        item {
                            NavigationDrawerItem(
                                label = { Text("All Examination") },
                                onClick = { viewModel.onSubject(-1) },
                                selected = currentSubjectIndex == -1L
                            )
                        }
                        items(subjects.value, key = { it.id }) {
                            NavigationDrawerItem(
                                label = { Text(it.name) },
                                onClick = { viewModel.onSubject(it.id) },
                                selected = currentSubjectIndex == it.id
                            )
                        }

                    }
                },
                content = {

                    MainContent(
                        modifier = Modifier
                            .padding(horizontal = 16.dp),
                        exams = viewModel.examUiStates.collectAsState().value,
                        examYearError = viewModel.dateError.value,
                        examUiState = viewModel.exam.value,
                        subjectUiState = viewModel.subject.value,
                        isSelectMode = viewModel.isSelectMode.value,
                        windowSizeClass = windowSizeClass,
                        subjects = subjects.value,
                        addExam = viewModel::addExam,
                        addSubject = viewModel::addSubject,
                        onExamClick = onExamClick,
                        toggleSelect = viewModel::toggleSelect,
                        onSubjectIdChange = viewModel::onSubjectIdChange,
                        onExamYearChange = viewModel::onExamYearContentChange,
                        onExamDurationChange = viewModel::onExamDurationContentChange,
                        onSubjectNameChange = viewModel::onSubjectContentChange,
                        onDeleteSubject = viewModel::onDeleteExam,
                        onUpdateSubject = viewModel::onUpdateExam,

                        )
                })
        }


    }
}

@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    subjects: ImmutableList<SubjectUiState> = emptyList<SubjectUiState>().toImmutableList(),
    exams: ImmutableList<ExamUiState> = emptyList<ExamUiState>().toImmutableList(),
    examUiState: ExamUiState,
    isSelectMode: Boolean = false,
    subjectUiState: SubjectUiState,
    examYearError: Boolean = false,
    windowSizeClass: WindowSizeClass,
    addSubject: () -> Unit = {},
    toggleSelect: (Long) -> Unit = {},
    addExam: () -> Unit = {},
    onExamClick: (Long, Long) -> Unit = { _, _ -> },
    onExamYearChange: (String) -> Unit = {},
    onExamDurationChange: (String) -> Unit = {},
    onSubjectIdChange: (Long) -> Unit = {},
    onSubjectNameChange: (String) -> Unit = {},
    onUpdateSubject: (Long) -> Unit = {},
    onDeleteSubject: (Long) -> Unit = {},
) {
    var showmenu by remember {
        mutableStateOf(false)
    }

    val subjectFocus = remember { FocusRequester() }

    LaunchedEffect(subjectUiState.focus) {
        if (subjectUiState.focus) {
            subjectFocus.requestFocus()
        }
    }

    CommonScreen(
        windowSizeClass,
        firstScreen = {
        LazyColumn(Modifier.fillMaxSize()) {
            items(exams, key = { it.id }) {
                ExamUi(
                    modifier = Modifier.clickable {
                        if (isSelectMode) {
                            toggleSelect(it.id)
                        } else {
                            onExamClick(it.id, it.subjectID)
                        }

                    },
                    examUiState = it,
                    onDelete = onDeleteSubject,
                    onUpdate = onUpdateSubject,
                    toggleSelect = toggleSelect,
                    isSelectMode = isSelectMode

                )
            }
        }


    }, secondScreen = {
        Column(
            Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text("Add Examination")
            Box {
                TextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = { Text("Subject") },
                    value = examUiState.subject,
                    onValueChange = {},
                    trailingIcon = {
                        IconButton(onClick = {
                            showmenu = !showmenu
                        }) { Icon(imageVector = Icons.Default.ArrowDropDown, "down") }
                    },
                    readOnly = true

                )
                DropdownMenu(
                    expanded = showmenu,
                    onDismissRequest = { showmenu = false }) {
                    subjects.forEach { subj ->
                        DropdownMenuItem(text = { Text(subj.name) },
                            onClick = {
                                onSubjectIdChange(subj.id)
                                showmenu = false
                            })
                    }
                }
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextField(
                    modifier = Modifier.weight(0.5f),
                    label = { Text("Year") },
                    value = if (examUiState.year != -1L) examUiState.year.toString() else "",
                    placeholder = { Text("2012") },
                    isError = examYearError,
                    onValueChange = { onExamYearChange(it) },
                    maxLines = 1,

                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )

                TextField(
                    modifier = Modifier.weight(0.5f),
                    label = { Text("Duration") },
                    value = if (examUiState.examTime != -1L) examUiState.examTime.toString() else "",
                    placeholder = { Text("15") },
                    suffix = { Text("Min") },
                    onValueChange = { onExamDurationChange(it) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }

            Button(
                modifier = Modifier.align(Alignment.End),
                enabled = examUiState.subject.isNotBlank() && examUiState.year != -1L,
                onClick = {
                    addExam()
                }) {
                Text("Add Exam")
            }
            Spacer(Modifier.width(16.dp))

            Text("Add Subject")
            TextField(
                modifier = Modifier.fillMaxWidth().focusRequester(subjectFocus),
                label = { Text("Subject") },
                placeholder = { Text("Mathematics") },
                value = subjectUiState.name,
                onValueChange = {
                    onSubjectNameChange(it)
                },
                keyboardOptions = KeyboardOptions(
                    capitalization = KeyboardCapitalization.Sentences,
                    autoCorrect = true
                ),
                maxLines = 1,
            )
            Button(
                modifier = Modifier.align(Alignment.End),
                enabled = subjectUiState.name.isNotBlank(),
                onClick = {
                    addSubject()
                }) {
                Text("Add Subject")
            }


        }
    })


}


//@Preview
@Composable
fun ContentPreview() {
    MaterialTheme {

    }
}
//
//@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
//@Preview
//@Composable
//fun id() {
//    MaterialTheme {
//
//        val state = rememberSplitPaneState(0.5f, true)
//        HorizontalSplitPane(splitPaneState = state) {
//            splitter {
//                this.visiblePart {
//                    Box(Modifier.background(Color.Yellow).width(10.dp).fillMaxHeight())
//                }
//                handle {
//                    Box(Modifier.background(Color.Blue).width(20.dp).fillMaxHeight().markAsHandle())
//                }
//            }
//            first {
//
//                Image(painter = painterResource("drawables/logo.png"), "")
//
//
//
//                Text("Abiola")
//            }
//            second {
//                Button(onClick = {}, content = {
//                    Text("Click")
//                    Icon(Icons.Default.Android, contentDescription = null)
//                })
//            }
//        }
//
//
//    }
//
//}

@Composable
fun MainDialog(
    show: Boolean,
    export: (String, String, String, Int) -> Unit = { _, _, _, _ -> },
    onClose: () -> Unit = {}
) {
    var showDir by remember {
        mutableStateOf(false)
    }
    var path by remember {
        mutableStateOf("")
    }
    var name by remember {
        mutableStateOf("")
    }
    var key by remember {
        mutableStateOf("")
    }
    var version by remember {
        mutableStateOf("")
    }
    if (show) {
        Dialog(onDismissRequest = onClose, properties = DialogProperties()) {
            Card {
                Column(
                    modifier = Modifier.padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Export Examination",
                        style = MaterialTheme.typography.titleMedium
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(modifier = Modifier.weight(0.3f), text = "Directory")
                        Text(modifier = Modifier.weight(0.6f), text = path)
                        IconButton(onClick = { showDir = true }) {
                            Icon(
                                modifier = Modifier.weight(0.1f),
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "select"
                            )
                        }

                    }

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        label = { Text("Name") },
                        placeholder = { Text("data") },
                        onValueChange = { name = it })
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = key,
                        label = { Text("Key") },
                        placeholder = { Text("SwordFish") },
                        onValueChange = { key = it })
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = version,
                        placeholder = { Text("1") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        label = { Text("Data version") },
                        onValueChange = { version = it })

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        TextButton(onClick = onClose) {
                            Text("Cancel")
                        }

                        Button(
                            onClick = {
                                export(
                                    path,
                                    name.ifBlank { "data" },
                                    key.ifBlank { "SwordFish" },
                                    version.toIntOrNull() ?: 0
                                )
                                path = ""
                                version = ""
                                key = ""
                                name = ""
                                onClose()

                            },
                            enabled = path.isNotBlank()
                        ) {
                            Text("Export")
                        }
                    }


                }
            }


        }
    }
    DirtoryUi(showDir, onDismiss = { showDir = false }) {
        it?.let {
            path = it.path
        }
    }
}

//@Preview
@Composable
fun MainDialogPreveiw() {
    SeriesAppTheme(true) {
        MainDialog(true)
    }


}


@Composable
fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit = {},
    onFile: (File?) -> Unit = {}
) {

    LaunchedEffect(show) {
        if (show) {
            val jFileChooser = JFileChooser()
            jFileChooser.dragEnabled = true
            jFileChooser.fileSelectionMode = JFileChooser.DIRECTORIES_ONLY
            val ret = jFileChooser.showSaveDialog(null)
            if (ret == JFileChooser.APPROVE_OPTION) {
                val file = jFileChooser.selectedFile
                onFile(file)
            }
            onDismiss()

        }
    }

}