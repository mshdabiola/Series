package com.mshdabiola.detail.instruction

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.mshdabiola.model.data.Type
import com.mshdabiola.mvvn.KoinCommonViewModel
import com.mshdabiola.ui.CommonScreen2
import com.mshdabiola.ui.ScreenSize
import com.mshdabiola.ui.instructionui.InstructionEditUi
import com.mshdabiola.ui.instructionui.InstructionUi
import com.mshdabiola.ui.state.InstruInputUiState
import com.mshdabiola.ui.state.InstructionUiState
import kotlinx.collections.immutable.ImmutableList
import org.koin.core.parameter.parameterSetOf


@Composable
internal fun InstructionRoute(
    screenSize: ScreenSize,
    examId : Long,
    subjectId:Long,
    onDismiss: () -> Unit = {},
    show: Boolean = false,
    ) {
    val viewModel: InstructionViewModel = KoinCommonViewModel(
        parameters = {
            parameterSetOf(
               examId
            )
        },
    )

    InstructionContent(
        instructionUiState = viewModel.instructionUiState.value,
        instructionUiStates = viewModel.instructions .value,
        instruInputUiState = viewModel.instruInputUiState.value,
        screenSize = screenSize,
        onTitleChange = viewModel::instructionTitleChange,
        addUp = viewModel::addUpInstruction,
        addBottom = viewModel::addDownInstruction,
        delete = viewModel::deleteInstruction,
        moveUp = viewModel::moveUpInstruction,
        moveDown = viewModel::moveDownInstruction,
        edit = viewModel::editInstruction,
        changeType = viewModel::changeTypeInstruction,
        onTextChange = viewModel::onTextChangeInstruction,
        onAddInstruction = viewModel::onAddInstruction,
        onDeleteInstruction = viewModel::onDeleteInstruction,
        onUpdateInstruction = viewModel::onUpdateInstruction,
        onAddInstruInputUiState = viewModel::onAddInstruTopicFromInput,
        onInstruInputChange = viewModel::onInstuInputChanged,
        show = show,
        onDismiss = onDismiss,
    )

}

@Composable
fun InstructionContent(
    modifier: Modifier = Modifier,
    instructionUiState: InstructionUiState,
    instructionUiStates: ImmutableList<InstructionUiState>,
    instruInputUiState: InstruInputUiState,
    screenSize: ScreenSize,
    show: Boolean = false,
    onTitleChange: (String) -> Unit = {},
    addUp: (Int) -> Unit = { _ -> },
    addBottom: (Int) -> Unit = { _ -> },
    delete: (Int) -> Unit = { _ -> },
    moveUp: (Int) -> Unit = { _ -> },
    moveDown: (Int) -> Unit = { _ -> },
    edit: (Int) -> Unit = { _ -> },
    changeType: (Int, Type) -> Unit = { _, _ -> },
    onTextChange: (Int, String) -> Unit = { _, _ -> },
    onAddInstruction: () -> Unit = {},
    onDeleteInstruction: (Long) -> Unit = {},
    onUpdateInstruction: (Long) -> Unit = {},
    onAddInstruInputUiState: () -> Unit = {},
    onInstruInputChange: (String) -> Unit = {},
    onDismiss: () -> Unit = {},
) {
    var showConvert by remember { mutableStateOf(false) }

    CommonScreen2(
        screenSize,
        firstScreen = {
            LazyColumn(it.fillMaxSize()) {
                items(
                    items = instructionUiStates,
                    key = { it.id },
                ) {
                    InstructionUi(
                        instructionUiState = it,
                        onUpdate = onUpdateInstruction,
                        onDelete = onDeleteInstruction,
                    )
                }
            }
        },
        secondScreen = {
            Column(modifier = Modifier.fillMaxWidth().verticalScroll(rememberScrollState())) {
                InstructionEditUi(
                    modifier = Modifier.fillMaxWidth(),
                    instructionUiState = instructionUiState,
                    onTitleChange = onTitleChange,
                    addUp = addUp,
                    addBottom = addBottom,
                    delete = delete,
                    moveUp = moveUp,
                    moveDown = moveDown,
                    edit = edit,
                    changeType = changeType,
                    onTextChange = onTextChange,
                )
                Spacer(Modifier.height(4.dp))
                Row(Modifier.fillMaxWidth()) {
                    Button(
                        onClick = onAddInstruction,
                        enabled = instructionUiState.content.first().content.isNotBlank(),
                    ) {
                        Text("Add Instruction")
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
                            value = instruInputUiState.content,
                            onValueChange = onInstruInputChange,
                            isError = instruInputUiState.isError,
                            modifier = Modifier.fillMaxWidth().height(300.dp),
                        )
                        Spacer(Modifier.height(4.dp))
                        Row(
                            Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically,
                        ) {
                            Text("* instruction title")
                            Button(
                                modifier = Modifier,
                                onClick = onAddInstruInputUiState,
                            ) {
                                Text("Convert to Instruction")
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