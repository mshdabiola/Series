package com.mshdabiola.series.feature.exam

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.ui.questionui.QuestionEditUi
import com.mshdabiola.ui.questionui.QuestionUi
import com.mshdabiola.ui.state.QuestionUiState
import kotlinx.collections.immutable.ImmutableList
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState

@OptIn(ExperimentalMaterial3Api::class)
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
    ) {

        val questions = viewModel.questions.collectAsState()
        ExamContent(
            modifier = Modifier.padding(it).padding(horizontal = 16.dp),
            questionUiState = viewModel.question.value,
            questions = questions.value,
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
            onMoveUpQuestion = viewModel::onMoveUpQuestion
        )
    }

}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    questions: ImmutableList<QuestionUiState>,
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
    onMoveDownQuestion: (Long) -> Unit = {}
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {
            LazyColumn(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(8.dp)) {
                items(questions,key = {it.id}) {
                    QuestionUi(
                        questionUiState = it,
                        onDelete = onDeleteQuestion,
                        onUpdate = onUpdateQuestion,
                        onMoveDown = onMoveDownQuestion,
                        onMoveUp = onMoveUpQuestion
                    )
                }
            }

        }
        second {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
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


            }


        }
    }

}
