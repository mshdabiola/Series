/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExtendedFloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.detail.instruction.InstructionRoute
import com.mshdabiola.detail.question.QuestionRoute
import com.mshdabiola.detail.question.QuestionViewModel
import com.mshdabiola.detail.topic.TopicRoute
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.ScreenSize
import org.koin.core.parameter.parameterSetOf

@Composable
internal fun DetailRoute(
    screenSize: ScreenSize,
    examId:Long,
    subjectId:Long,
    onShowSnackbar: suspend (String, String?) -> Boolean,
    onBack: () -> Unit,
) {
    ExamScreen(examId,subjectId,screenSize,onBack)
}

//@OptIn(ExperimentalMaterial3Api::class)
//@VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
//@Composable
//internal fun DetailScreen(
//    modifier: Modifier = Modifier,
//    title: String = "",
//    content: String = "",
//    screenSize: ScreenSize = ScreenSize.COMPACT,
//
//    onTitleChange: (String) -> Unit = {},
//    onContentChange: (String) -> Unit = {},
//    onShowSnackbar: suspend (String, String?) -> Boolean = { _, _ -> false },
//    onBack: () -> Unit = {},
//    onDelete: () -> Unit = {},
//) {
//    val coroutineScope = rememberCoroutineScope()
//    Column(modifier) {
//        DetailTopAppBar(
//            onNavigationClick = onBack,
//            onDeleteClick = onDelete,
//        )
//        SkTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .testTag("detail:title"),
//            value = title,
//            onValueChange = onTitleChange,
//            placeholder = "Title",
//            maxNum = 1,
//            imeAction = ImeAction.Next,
//        )
//        SkTextField(
//            modifier = Modifier
//                .fillMaxWidth()
//                .testTag("detail:content")
//                .weight(1f),
//            value = content,
//            onValueChange = onContentChange,
//            placeholder = "content",
//            imeAction = ImeAction.Done,
//            keyboardAction = { coroutineScope.launch { onShowSnackbar("Note Update", null) } },
//        )
//    }
//
//    TrackScreenViewEvent(screenName = "Detail")
//}

//
//@Composable
//private fun DetailScreenPreview() {
//    DetailScreen()
//}


@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun ExamScreen(
    examId:Long,
    subjectId:Long,
    screenSize: ScreenSize,
    onBack: () -> Unit = {},

    ) {


    val action: @Composable RowScope.() -> Unit = {
        IconButton(onClick = onBack) {
            Icon(Icons.Default.ArrowBack, "back")
        }
    }
    var show by remember { mutableStateOf(false) }
    val onDismiss = { show = false }
    val questionViewModel: QuestionViewModel = KoinCommonViewModel(
        parameters = {
            parameterSetOf(
                examId, subjectId
            )
        },
    )

    val question = questionViewModel.question.value


    Scaffold(
        topBar = {
            if (screenSize == ScreenSize.EXPANDED) {
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
            if (screenSize != ScreenSize.EXPANDED) {
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

        var state by remember {
            mutableStateOf(0)
        }
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
                     QuestionRoute(
                         screenSize = screenSize,
                         examId = examId,
                         subjectId = subjectId,
                         onDismiss = onDismiss,
                         show = show,
                        viewModel = questionViewModel

                     )
                    1 ->
                        InstructionRoute(
                            screenSize = screenSize,
                            examId = examId,
                            subjectId = subjectId,
                            onDismiss = onDismiss,
                            show = show,
                            currentInstruction = question.instructionUiState?.id,
                            setInstruction = questionViewModel::onInstructionIdChange
                        )


                    else ->
                        TopicRoute(
                            screenSize = screenSize,
                            examId = examId,
                            subjectId = subjectId,
                            onDismiss = onDismiss,
                            show = show,
                            currentTopic = question.topicUiState?.id,
                            setTopic = questionViewModel::onTopicInputChanged
                        )

                }
            }
        }
    }
}


