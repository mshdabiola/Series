package com.mshdabiola.detail.topic

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.unit.dp
import com.mshdabiola.detail.instruction.InstructionViewModel
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.CommonScreen2
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.state.TopicInputUiState
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.topicui.TopicUi
import kotlinx.collections.immutable.ImmutableList
import org.koin.core.parameter.parameterSetOf


@Composable
internal fun TopicRoute(
    screenSize: ScreenSize,
    examId : Long,
    subjectId:Long
) {
    val viewModel: TopicViewModel = KoinCommonViewModel(
        parameters = {
            parameterSetOf(
                subjectId
            )
        },
    )

    var show by remember { mutableStateOf(false) }
    val onDismiss = { show = false }

    TopicContent(
        topicUiState = viewModel.topicUiState.value,
        topicUiStates = viewModel.topicUiStates.value,
        topicInputUiState = viewModel.topicInputUiState.value,
        screenSize = screenSize,
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
@Composable
fun TopicContent(
    modifier: Modifier = Modifier,
    topicUiState: TopicUiState,
    topicInputUiState: TopicInputUiState,
    topicUiStates: ImmutableList<TopicUiState>,
    screenSize: ScreenSize,
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
        screenSize = screenSize,
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

