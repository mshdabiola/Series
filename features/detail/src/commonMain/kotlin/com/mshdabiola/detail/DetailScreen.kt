/*
 *abiola 2022
 */

package com.mshdabiola.detail

import androidx.annotation.VisibleForTesting
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
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.mshdabiola.designsystem.component.DetailTopAppBar
import com.mshdabiola.designsystem.component.SkTextField
import com.mshdabiola.detail.instruction.InstructionContent
import com.mshdabiola.detail.instruction.InstructionRoute
import com.mshdabiola.detail.question.ExamContent
import com.mshdabiola.detail.question.QuestionRoute
import com.mshdabiola.detail.topic.TopicContent
import com.mshdabiola.detail.topic.TopicRoute
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.CommonScreen2
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.TemplateUi
import com.mshdabiola.ui.TrackScreenViewEvent
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
                     QuestionRoute(screenSize,examId,subjectId,onDismiss,show)
                    1 ->
                        InstructionRoute(screenSize,examId,subjectId,onDismiss,show)


                    else ->
                        TopicRoute(screenSize,examId,subjectId,onDismiss,show)

                }
            }
        }
    }
}


