/*
 *abiola 2022
 */

package com.mshdabiola.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.HdrOnSelect
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.MoreHoriz
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SelectAll
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.Subject
import androidx.compose.material.icons.filled.Update
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Deselect
import androidx.compose.material.icons.rounded.SaveAs
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import coil3.compose.AsyncImage
import com.mshdabiola.model.Image
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.mvvn.collectAsStateWithLifecycleCommon
import com.mshdabiola.ui.CommonScreen
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.examui.ExamUi
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import java.io.File

// import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainRoute(
    screenSize: ScreenSize,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onClicked: (Long, Long) -> Unit,
    navigateToSetting: () -> Unit,
    navigateToDetail: (Long, Long) -> Unit,

//    viewModel: MainViewModel,
) {
    val viewModel: MainViewModel = KoinCommonViewModel()

    val mainState = viewModel.mainState.collectAsStateWithLifecycleCommon().value
    // var show by remember { mutableStateOf(false) }

    var showDrop by remember { mutableStateOf(false) }
    var deleteId by remember { mutableStateOf<Long?>(null) }
    var showDialog by remember {
        mutableStateOf(false)
    }
    MainDialog(showDialog, viewModel::onExport) { showDialog = false }
//    DirtoryUi(show, onDismiss = { show = false }, {
//        it?.let {
//            viewModel.onExport(it.path)
//        }
//    }
    // )
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val coroutineScope = rememberCoroutineScope()
    val action: @Composable RowScope.() -> Unit = {
        IconButton(
            onClick = navigateToSetting,
            // enabled = currentSubjectIndex > -1
        ) {
            Icon(Icons.Default.Settings, "setting")
        }
        Box {
            IconButton(
                onClick = { showDrop = true },
                // enabled = currentSubjectIndex > -1
            ) {
                Icon(Icons.Default.MoreVert, "more")
            }

            if (mainState.isSelectMode) {
                DropdownMenu(
                    expanded = showDrop,
                    onDismissRequest = { showDrop = false },
                ) {
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Default.SelectAll,
                                "select All",
                            )
                        },
                        text = { Text("Select All") },
                        onClick = {
                            viewModel.selectAll()
                        },
                    )

                    if (mainState.currentSubjectId > -1) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Subject,
                                    "select all subject",
                                )
                            },
                            text = { Text("Select Current Subject") },
                            onClick = {
                                viewModel.selectAllSubject()
                            },
                        )
                    }
                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Deselect,
                                "deselect",
                            )
                        },
                        text = { Text("DeSelect All") },
                        onClick = {
                            viewModel.deselectAll()
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.SaveAs,
                                "save",
                            )
                        },
                        text = { Text("Export Selected") },
                        onClick = {
                            // onDelete(examUiState.id)
                            showDialog = true
                            showDrop = false
                        },
                    )

                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Rounded.Delete,
                                "delete",
                            )
                        },
                        text = { Text("Delete selected") },
                        onClick = {
                            deleteId = -1
                            showDrop = false
                        },
                    )
                }
            } else {
                DropdownMenu(
                    expanded = showDrop,
                    onDismissRequest = { showDrop = false },
                ) {
                    if (mainState.currentSubjectId > -1) {
                        DropdownMenuItem(
                            leadingIcon = {
                                Icon(
                                    Icons.Default.Update,
                                    "update",
                                )
                            },
                            text = { Text("Update") },
                            onClick = {
                                viewModel.updateSubject(mainState.currentSubjectId)
                                showDrop = false
                            },
                        )
                    }

                    DropdownMenuItem(
                        leadingIcon = {
                            Icon(
                                Icons.Default.HdrOnSelect,
                                "select mode",
                            )
                        },
                        text = { Text("Select mode") },
                        onClick = {
                            viewModel.toggleSelectMode()
                            showDrop = false
                        },
                    )

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
    val topbar: @Composable () -> Unit = {
        //  if (windowSizeClass.widthSizeClass== WindowWidthSizeClass.Expanded) {
        TopAppBar(
            title = { Text("Main Screen") },
            actions = action,
        )
        // }
    }

    if (screenSize == ScreenSize.COMPACT) {
        ModalNavigationDrawer(
            modifier = Modifier,
            drawerState = drawerState,
            drawerContent = {
                Card(
                    modifier = Modifier.fillMaxHeight().width(300.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    shape = RoundedCornerShape(0.dp, 8.dp, 8.dp, 0.dp),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight()
                            .systemBarsPadding()
                            .padding(16.dp),
                    ) {
                        item {
                            NavigationDrawerItem(
                                modifier = Modifier.fillMaxWidth(),

                                label = { Text("All Examination") },
                                onClick = {
                                    viewModel.onSubject(-1)

                                    coroutineScope.launch { drawerState.close() }
                                },
                                selected = mainState.currentSubjectId == -1L,
                            )
                        }
                        items(mainState.subjects, key = { it.id }) {
                            NavigationDrawerItem(
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(it.name) },
                                onClick = {
                                    viewModel.onSubject(it.id)
                                    coroutineScope.launch { drawerState.close() }
                                },
                                selected = mainState.currentSubjectId == it.id,
                            )
                        }
                    }
                }
            },
            content = {
                MainScreen(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    exams = mainState.examinations,
                    examYearError = mainState.dateError,
                    examUiState = mainState.examination,
                    subjectUiState = mainState.subject,
                    isSelectMode = mainState.isSelectMode,
                    screenSize = screenSize,
                    subjects = mainState.subjects,
                    addExam = viewModel::addExam,
                    addSubject = viewModel::addSubject,
                    onExamClick = navigateToDetail,
                    toggleSelect = viewModel::toggleSelect,
                    onSubjectIdChange = viewModel::onSubjectIdChange,
                    onExamYearChange = viewModel::onExamYearContentChange,
                    onExamDurationChange = viewModel::onExamDurationContentChange,
                    onSubjectNameChange = viewModel::onSubjectContentChange,
                    onDeleteSubject = { deleteId = it },
                    onUpdateSubject = viewModel::onUpdateExam,
                    action = {
                        IconButton(onClick = {
                            coroutineScope.launch {
                                if (drawerState.isOpen) {
                                    drawerState.close()
                                } else {
                                    drawerState.open()
                                }
                            }
                        }) {
                            Icon(Icons.Default.Menu, "menu")
                        }
                        action()
                    },
                )
            },
        )
    } else {
        PermanentNavigationDrawer(
            modifier = Modifier,
            drawerContent = {
                Card(
                    modifier = Modifier.fillMaxHeight().width(300.dp),
                    colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.background),
                    shape = RectangleShape,
                    elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                ) {
                    LazyColumn(
                        modifier = Modifier.fillMaxHeight()
                            .systemBarsPadding()
                            .padding(16.dp),
                    ) {
                        item {
                            NavigationDrawerItem(
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text("All Examination") },
                                onClick = { viewModel.onSubject(-1) },
                                selected = mainState.currentSubjectId == -1L,
                            )
                        }
                        items(mainState.subjects, key = { it.id }) {
                            NavigationDrawerItem(
                                modifier = Modifier.fillMaxWidth(),
                                label = { Text(it.name) },
                                onClick = { viewModel.onSubject(it.id) },
                                selected = mainState.currentSubjectId == it.id,
                            )
                        }
                    }
                }
            },
            content = {
                MainScreen(
                    modifier = Modifier
                        .padding(horizontal = 16.dp),
                    exams = mainState.examinations,
                    examYearError = mainState.dateError,
                    examUiState = mainState.examination,
                    subjectUiState = mainState.subject,
                    isSelectMode = mainState.isSelectMode,
                    screenSize = screenSize,
                    subjects = mainState.subjects,
                    addExam = viewModel::addExam,
                    addSubject = viewModel::addSubject,
                    onExamClick = navigateToDetail,
                    toggleSelect = viewModel::toggleSelect,
                    onSubjectIdChange = viewModel::onSubjectIdChange,
                    onExamYearChange = viewModel::onExamYearContentChange,
                    onExamDurationChange = viewModel::onExamDurationContentChange,
                    onSubjectNameChange = viewModel::onSubjectContentChange,
                    onDeleteSubject = { deleteId = it },
                    onUpdateSubject = viewModel::onUpdateExam,
                    action = action,
                    topbar = topbar,

                    )
            },
        )
    }
    if (deleteId != null) {
        AlertDialog(
            onDismissRequest = { deleteId = null },
            dismissButton = {
                TextButton(onClick = { deleteId = null }) {
                    Text("Cancel")
                }
            },
            confirmButton = {
                ElevatedButton(onClick = {
                    if (deleteId == -1L) {
                        viewModel.deleteSelected()
                    } else {
                        viewModel.onDeleteExam(deleteId!!)
                    }
                    deleteId = null
                }) {
                    Text("Delete exam")
                }
            },
            icon = { Icon(Icons.Default.Delete, "delete") },
            title = { Text(text = "Delete Subject") },
            text = {
                Text("Are you sure you want to delete this examination?")
            },
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalComposeUiApi::class)
@Composable
internal fun MainScreen(
    modifier: Modifier = Modifier,
    subjects: ImmutableList<SubjectUiState> = emptyList<SubjectUiState>().toImmutableList(),
    exams: ImmutableList<ExamUiState> = emptyList<ExamUiState>().toImmutableList(),
    examUiState: ExamUiState?,
    isSelectMode: Boolean = false,
    subjectUiState: SubjectUiState,
    examYearError: Boolean = false,
    screenSize: ScreenSize,
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
    action: @Composable RowScope.() -> Unit = {},
    topbar: @Composable () -> Unit = {},
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
        screenSize = screenSize,
        firstScreen = {
            LazyColumn(it.fillMaxSize()) {
                items(exams, key = { it.id }) {
                    ExamUi(
                        modifier = Modifier.clickable {
                            if (isSelectMode) {
                                toggleSelect(it.id)
                            } else {
                                onExamClick(it.id, it.subject.id)
                            }
                        },
                        examUiState = it,
                        onDelete = onDeleteSubject,
                        onUpdate = onUpdateSubject,
                        toggleSelect = toggleSelect,
                        isSelectMode = isSelectMode,

                        )
                }
            }
        },
        secondScreen = {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Text("Add Examination")
                com.mshdabiola.ui.DropdownMenu(
                    modifier = Modifier.fillMaxWidth(),
                    currentIndex = subjects.indexOfFirst { it.name == examUiState?.subject?.name },
                    data = subjects.map { it.name }.toImmutableList(),
                    onDataChange = {
                        onSubjectIdChange(it.toLong())
                    },
                    label = "Subject",
                )
//                Box {
//                    TextField(
//                        modifier = Modifier.fillMaxWidth(),
//                        label = { Text("Subject") },
//                        value = examUiState.subject,
//                        onValueChange = {},
//                        trailingIcon = {
//                            IconButton(onClick = {
//                                showmenu = !showmenu
//                            }) { Icon(imageVector = Icons.Default.ArrowDropDown, "down") }
//                        },
//                        readOnly = true,
//
//                    )
//                    DropdownMenu(
//                        expanded = showmenu,
//                        onDismissRequest = { showmenu = false },
//                    ) {
//                        subjects.forEach { subj ->
//                            DropdownMenuItem(
//                                text = { Text(subj.name) },
//                                onClick = {
//                                    onSubjectIdChange(subj.id)
//                                    showmenu = false
//                                },
//                            )
//                        }
//                    }
//                }
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    TextField(
                        modifier = Modifier.weight(0.5f),
                        label = { Text("Year") },
                        value = if (examUiState?.year != -1L) examUiState?.year.toString() else "",
                        placeholder = { Text("2012") },
                        isError = examYearError,
                        onValueChange = { onExamYearChange(it) },
                        maxLines = 1,

                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )

                    TextField(
                        modifier = Modifier.weight(0.5f),
                        label = { Text("Duration") },
                        value = if (examUiState?.duration != -1L) examUiState?.duration.toString() else "",
                        placeholder = { Text("15") },
                        suffix = { Text("Min") },
                        onValueChange = { onExamDurationChange(it) },
                        maxLines = 1,
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    )
                }

                Button(
                    modifier = Modifier.align(Alignment.End),
                    enabled = examUiState?.subject?.name?.isNotBlank() == true && examUiState.year != -1L,
                    onClick = {
                        addExam()
                    },
                ) {
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
                        autoCorrect = true,
                    ),
                    maxLines = 1,
                )
                Button(
                    modifier = Modifier.align(Alignment.End),
                    enabled = subjectUiState.name.isNotBlank(),
                    onClick = {
                        addSubject()
                    },
                ) {
                    Text("Add Subject")
                }
            }
        },
        action = action,
        topbar = topbar,
    )
}

// @Preview
@Composable
fun ContentPreview() {
    MaterialTheme {
    }
}
//
// @OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
// @Preview
// @Composable
// fun id() {
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
// }

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun MainDialog(
    show: Boolean,
    export: (String, String, String, Int) -> Unit = { _, _, _, _ -> },
    onClose: () -> Unit = {},
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
                    verticalArrangement = Arrangement.spacedBy(8.dp),
                ) {
                    Text(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        text = "Export Examination",
                        style = MaterialTheme.typography.titleMedium,
                    )
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Text(modifier = Modifier.weight(0.3f), text = "Directory")
                        Text(
                            modifier = Modifier.weight(0.6f).basicMarquee(),
                            text = path,
                            maxLines = 1,
                        )
                        IconButton(onClick = { showDir = true }) {
                            Icon(
                                modifier = Modifier.weight(0.1f),
                                imageVector = Icons.Default.MoreHoriz,
                                contentDescription = "select",
                            )
                        }
                    }

                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = name,
                        label = { Text("Name") },
                        placeholder = { Text("data") },
                        onValueChange = { name = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        maxLines = 1,

                        )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = key,
                        label = { Text("Key") },
                        placeholder = { Text("SwordFish") },
                        onValueChange = { key = it },
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
                        maxLines = 1,
                    )
                    TextField(
                        modifier = Modifier.fillMaxWidth(),
                        value = version,
                        placeholder = { Text("1") },
                        label = { Text("Data version") },
                        onValueChange = { version = it },
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Next,
                            keyboardType = KeyboardType.Number,
                        ),
                        maxLines = 1,
                    )

                    Row(
                        modifier = Modifier.align(Alignment.End),
                        horizontalArrangement = Arrangement.spacedBy(8.dp),
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
                                    version.toIntOrNull() ?: 0,
                                )
                                path = ""
                                version = ""
                                key = ""
                                name = ""
                                onClose()
                            },
                            enabled = path.isNotBlank(),
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
//
// @Preview
// @Composable
// fun MainScreenPreview() {
//    MainScreen(
//        mainState = MainState(),
//       items =listOf(ModelUiState(2, "")).toImmutableList()
//    )
// }

@Composable
fun ItemImage(imageModel: Image) {
    ListItem(
        headlineContent = { Text(imageModel.user ?: "name") },
        leadingContent = {
            AsyncImage(
                modifier = Modifier.size(150.dp),
                model = imageModel.url,
                contentDescription = null,
            )
        },
    )
}


// @Preview
@Composable
fun MainDialogPreveiw() {
//    SeriesTheme {
//        MainDialog(true)
//    }
}

@Composable
expect fun DirtoryUi(
    show: Boolean,
    onDismiss: () -> Unit = {},
    onFile: (File?) -> Unit = {},
)
