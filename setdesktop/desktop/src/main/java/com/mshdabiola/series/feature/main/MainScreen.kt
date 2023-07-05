package com.mshdabiola.series.feature.main

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Android
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.SaveAs
import androidx.compose.material.icons.filled.Update
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mshdabiola.ui.DirtoryUi
import com.mshdabiola.ui.examui.ExamUi
import com.mshdabiola.ui.state.ExamUiState
import com.mshdabiola.ui.state.SubjectUiState
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.toImmutableList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(
    viewModel: MainViewModel,
    onExamClick: (Long, Long) -> Unit = { _, _ -> }
) {
    var show by remember { mutableStateOf(false) }

    var showDrop by remember { mutableStateOf(false) }
    val subjects = viewModel.subjects.collectAsState()
    val currentSubjectIndex = viewModel.currentSubjectId.collectAsState().value
    DirtoryUi(show, onDismiss = { show = false }, {
        it?.let {
            viewModel.onExport(it.path)
        }
    }
    )
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Main Screen") },
                actions = {
                    Box {
                        IconButton(
                            onClick = { showDrop = true },
                            enabled = currentSubjectIndex > -1
                        ) {
                            androidx.compose.material.Icon(Icons.Default.MoreVert, "more")
                        }
                        DropdownMenu(expanded = showDrop, onDismissRequest = { showDrop = false }) {


                            DropdownMenuItem(
                                leadingIcon = {
                                    androidx.compose.material.Icon(
                                        Icons.Default.Update,
                                        "update"
                                    )
                                },
                                text = { Text("Update") },
                                onClick = {
                                    viewModel.updateSubject(currentSubjectIndex)
                                    showDrop = false
                                })
                            DropdownMenuItem(
                                leadingIcon = {
                                    androidx.compose.material.Icon(
                                        Icons.Default.SaveAs,
                                        "export"
                                    )
                                },
                                text = { Text("Export") },
                                onClick = {
                                    // onDelete(examUiState.id)
                                    show = true
                                    showDrop = false
                                })

                            DropdownMenuItem(
                                leadingIcon = {
                                    androidx.compose.material.Icon(
                                        Icons.Default.Delete,
                                        "Delete"
                                    )
                                },
                                text = { Text("Delete") },
                                onClick = {

                                })


                        }
                    }
                }
            )
        }
    ) { paddingValues ->

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
                    subjects = subjects.value,
                    addExam = viewModel::addExam,
                    addSubject = viewModel::addSubject,
                    onExamClick = onExamClick,
                    onSubjectIdChange = viewModel::onSubjectIdChange,
                    onExamNameChange = viewModel::onExamYearContentChange,
                    onSubjectNameChange = viewModel::onSubjectContentChange,
                    onDeleteSubject = viewModel::onDeleteExam,
                    onUpdateSubject = viewModel::onUpdateExam,
                )
            })

    }
}

@OptIn(ExperimentalSplitPaneApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    subjects: ImmutableList<SubjectUiState> = emptyList<SubjectUiState>().toImmutableList(),
    exams: ImmutableList<ExamUiState> = emptyList<ExamUiState>().toImmutableList(),
    examUiState: ExamUiState,
    subjectUiState: SubjectUiState,
    examYearError: Boolean = false,
    addSubject: () -> Unit = {},
    addExam: () -> Unit = {},
    onExamClick: (Long, Long) -> Unit = { _, _ -> },
    onExamNameChange: (String) -> Unit = {},
    onSubjectIdChange: (Long) -> Unit = {},
    onSubjectNameChange: (String) -> Unit = {},
    onUpdateSubject: (Long) -> Unit = {},
    onDeleteSubject: (Long) -> Unit = {},
) {
    val state = rememberSplitPaneState(0.7f, true)
    var showmenu by remember {
        mutableStateOf(false)
    }

    val subjectFocus = remember { FocusRequester() }

    LaunchedEffect(subjectUiState.focus) {
        if (subjectUiState.focus) {
            subjectFocus.requestFocus()
        }
    }

    //Todo adjust home drawer

    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(Modifier.fillMaxSize()) {
                items(exams, key = { it.id }) {
                    ExamUi(
                        modifier = Modifier.clickable {
                            onExamClick(it.id, it.subjectID)
                        },
                        examUiState = it,
                        onDelete = onDeleteSubject,
                        onUpdate = onUpdateSubject

                    )
                }
            }


        }
        second {
            Column(
                Modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text("Add Examination")
                Box {
                    TextField(
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
                TextField(
                    label = { Text("Year") },
                    value = if (examUiState.year != -1L) examUiState.year.toString() else "",
                    placeholder = { Text("2012") },
                    isError = examYearError,
                    onValueChange = { onExamNameChange(it) },
                    maxLines = 1,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
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
                    modifier = Modifier.focusRequester(subjectFocus),
                    label = { Text("Subject") },
                    placeholder = { Text("Mathematics") },
                    value = subjectUiState.name,
                    onValueChange = {
                        onSubjectNameChange(it)
                    },
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

        }
    }

}


@Preview
@Composable
fun ContentPreview() {
    MaterialTheme {

    }
}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
@Preview
@Composable
fun id() {
    MaterialTheme {

        val state = rememberSplitPaneState(0.5f, true)
        HorizontalSplitPane(splitPaneState = state) {
            splitter {
                this.visiblePart {
                    Box(Modifier.background(Color.Yellow).width(10.dp).fillMaxHeight())
                }
                handle {
                    Box(Modifier.background(Color.Blue).width(20.dp).fillMaxHeight().markAsHandle())
                }
            }
            first {

                Image(painter = painterResource("drawables/logo.png"), "")



                Text("Abiola")
            }
            second {
                Button(onClick = {}, content = {
                    Text("Click")
                    Icon(Icons.Default.Android, contentDescription = null)
                })
            }
        }


    }

}