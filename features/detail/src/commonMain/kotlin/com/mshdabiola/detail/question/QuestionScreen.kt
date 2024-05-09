package com.mshdabiola.detail.question

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.SuggestionChip
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.detail.instruction.InstructionViewModel
import com.mshdabiola.model.data.Type
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.CommonScreen2
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.TemplateUi
import com.mshdabiola.ui.questionui.QuestionEditUi
import com.mshdabiola.ui.questionui.QuestionUi
import com.mshdabiola.ui.state.ExamInputUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import kotlinx.collections.immutable.ImmutableList
import org.koin.core.parameter.parameterSetOf


@Composable
internal fun QuestionRoute(
    screenSize: ScreenSize,
    examId : Long,
    subjectId:Long,
    onDismiss: () -> Unit = {},
    show: Boolean = false,
) {
    val viewModel: QuestionViewModel = KoinCommonViewModel(
        parameters = {
            parameterSetOf(
                examId,subjectId
            )
        },
    )


    ExamContent(
        modifier = Modifier,
        questionUiState = viewModel.question.value,
        questions = viewModel.questions.value,
        instructIdError = viewModel.instructIdError.value,
      //  topicUiStates = topicUiStates.value,
        examInputUiState = viewModel.examInputUiState.value,
        screenSize = screenSize,
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
   // topicUiStates: ImmutableList<TopicUiState>,
    examInputUiState: ExamInputUiState,
    screenSize: ScreenSize,
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
        rememberUpdatedState(screenSize != ScreenSize.EXPANDED)

    CommonScreen2(
        screenSize,
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


                        OutlinedTextField(
                            modifier = Modifier.weight(0.5f),
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
                        onClick = { onAddAnswer(questionUiState.answers == null) },
                        label = {
                            Text(
                                if (questionUiState.answers == null) "Add Answers" else "Remove answer",
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
