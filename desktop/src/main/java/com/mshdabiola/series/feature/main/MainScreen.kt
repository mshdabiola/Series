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
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.ExamWithSub
import com.mshdabiola.model.data.Subject
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
    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Main Screen") })
        }
    ) {
        MainContent(
            modifier = Modifier
                .padding(it)
                .padding(horizontal = 16.dp),
            exams = viewModel.subAndExams.collectAsState().value,
            examIndex = viewModel.examIndex.value,
            examYear = viewModel.examYear.value,
            examYearError = viewModel.dateError.value,
            subjectName = viewModel.subject.value,
            subjects = viewModel.subjects.collectAsState().value,
            addExam = viewModel::addExam,
            addSubject = viewModel::addSubject,
            onExamClick = onExamClick,
            onExamIndexChange = viewModel::onExamIndexContentChange,
            onExamNameChange = viewModel::onExamYearContentChange,
            onSubjectNameChange = viewModel::onSubjectContentChange
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalSplitPaneApi::class)
@Composable
fun MainContent(
    modifier: Modifier = Modifier,
    subjects: List<Subject> = emptyList(),
    exams: List<ExamWithSub> = emptyList(),
    examIndex: Int = 0,
    examYear: String = "",
    examYearError: Boolean = false,
    subjectName: String = "",
    addSubject: () -> Unit = {},
    addExam: () -> Unit = {},
    onExamClick: (Long, Long) -> Unit = { _, _ -> },
    onExamNameChange: (String) -> Unit = {},
    onExamIndexChange: (Int) -> Unit = {},
    onSubjectNameChange: (String) -> Unit = {}
) {
    val state = rememberSplitPaneState(0.7f, true)
    var showmenu by remember {
        mutableStateOf(false)
    }


    PermanentNavigationDrawer(
        modifier = modifier
            .fillMaxSize(),
        drawerContent = {
            LazyColumn(modifier = Modifier.width(200.dp)) {
                item {
                    NavigationDrawerItem(
                        label = { Text("Navigation") },
                        onClick = {},
                        selected = true
                    )
                }
                items(subjects, key = { it.id }) {
                    NavigationDrawerItem(
                        label = { Text(it.name) },
                        onClick = {},
                        selected = false
                    )
                }

            }
        },
        content = {

            HorizontalSplitPane(splitPaneState = state) {
                first {
                    LazyColumn(Modifier.fillMaxSize()) {
                        items(exams, key = { it.id }) {
                            ListItem(
                                modifier = Modifier.clickable {
                                    onExamClick(it.id, it.subjectID)
                                },
                                headlineText = { Text(it.subject) },
                                supportingText = { Text(it.year.toString()) }
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
                                value = subjects.getOrNull(examIndex)?.name ?: "",
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
                                subjects.forEachIndexed { index, subj ->
                                    DropdownMenuItem(text = { Text(subj.name) },
                                        onClick = {
                                            onExamIndexChange(index)
                                            showmenu = false
                                        })
                                }
                            }
                        }
                        TextField(
                            label = { Text("Year") },
                            value = examYear,
                            placeholder = { Text("2012") },
                            isError = examYearError,
                            onValueChange = { onExamNameChange(it) },
                            maxLines = 1,
                            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                        )
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            enabled = subjects.getOrNull(examIndex) != null && !examYearError && examYear.isNotBlank(),
                            onClick = {
                                addExam()
                            }) {
                            Text("Add Exam")
                        }
                        Spacer(Modifier.width(16.dp))

                        Text("Add Subject")
                        TextField(
                            label = { Text("Subject") },
                            placeholder = { Text("Mathematics") },
                            value = subjectName,
                            onValueChange = {
                                onSubjectNameChange(it)
                            },
                            maxLines = 1,
                        )
                        Button(
                            modifier = Modifier.align(Alignment.End),
                            enabled = subjectName.isNotBlank(),
                            onClick = {
                                addSubject()
                            }) {
                            Text("Add Subject")
                        }

                    }

                }
            }
        })
}


@Preview
@Composable
fun ContentPreview() {
    MaterialTheme {
        MainContent(
            exams = listOf(
                ExamWithSub(id = 1, subjectID = 1, year = 2019, subject = "Mathematics"),
                ExamWithSub(id = 2, subjectID = 1, year = 2020, subject = "Physic"),
                ExamWithSub(id = 3, subjectID = 1, year = 2021, subject = "Chemistry"),
                ExamWithSub(id = 4, subjectID = 1, year = 2022, subject = "English"),
                ExamWithSub(id = 5, subjectID = 1, year = 2024, subject = "Yoruba"),
                ExamWithSub(id = 6, subjectID = 1, year = 2025, subject = "Economics")
            ),
            subjects = listOf(
                Subject(id = 1, name = "Mathematics"),
                Subject(id = 2, name = "Physic"),
                Subject(id = 3, name = "Chemistry"),
                Subject(id = 4, name = "English"),
                Subject(id = 5, name = "Yoruba"),
                Subject(id = 6, name = "Economics")

            )
        )
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