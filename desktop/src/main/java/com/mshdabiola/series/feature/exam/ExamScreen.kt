package com.mshdabiola.series.feature.exam

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import com.mshdabiola.series.feature.exam.component.QuestionWholeUi
import com.mshdabiola.series.feature.exam.state.QuestionUiState
import com.mshdabiola.ui.image.AsyncImage
import com.mshdabiola.ui.image.loadImageBitmap
import com.mshdabiola.ui.image.loadSvgPainter
import com.mshdabiola.ui.image.loadXmlImageVector
import org.jetbrains.compose.resources.ExperimentalResourceApi
import org.jetbrains.compose.splitpane.ExperimentalSplitPaneApi
import org.jetbrains.compose.splitpane.HorizontalSplitPane
import org.jetbrains.compose.splitpane.rememberSplitPaneState
import java.awt.Desktop
import java.io.File

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
        ExamContent(
            modifier = Modifier.padding(it),
            questionUiState = viewModel.question.value,
            questions = viewModel.questions,
            addUp = viewModel::addUP,
            addBottom = viewModel::addDown,
            moveUp = viewModel::moveUP,
            moveDown = viewModel::moveDown,
            edit = viewModel::edit,
            delete = viewModel::delete,
            changeType = viewModel::changeType,
            onTextChange = viewModel::onTextChange
        )
    }

}

@OptIn(ExperimentalSplitPaneApi::class, ExperimentalResourceApi::class)
@Composable
fun ExamContent(
    modifier: Modifier = Modifier,
    questionUiState: QuestionUiState,
    questions: List<QuestionUiState>,
    addUp: (Int, Int) -> Unit = { _, _ -> },
    addBottom: (Int, Int) -> Unit = { _, _ -> },
    delete: (Int, Int) -> Unit = { _, _ -> },
    moveUp: (Int, Int) -> Unit = { _, _ -> },
    moveDown: (Int, Int) -> Unit = { _, _ -> },
    edit: (Int, Int) -> Unit = { _, _ -> },
    changeType: (Int, Int, Int) -> Unit = { _, _, _ -> },
    onTextChange: (Int, Int, String) -> Unit = { _, _, _ -> }
) {
    val state = rememberSplitPaneState(initialPositionPercentage = 0.5f)
    HorizontalSplitPane(
        modifier = modifier,
        splitPaneState = state
    ) {
        first {

        }
        second {

            QuestionWholeUi(
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
        }
    }

}
