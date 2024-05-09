package com.mshdabiola.detail.instruction

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import com.mshdabiola.data.Converter
import com.mshdabiola.data.SvgObject
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.model.ImageUtil.getGeneralDir
import com.mshdabiola.model.data.Type
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.InstruInputUiState
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.toInstruction
import com.mshdabiola.ui.toInstructionUiState
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class InstructionViewModel(
    private val examId : Long,
    private val instructionRepository: IInstructionRepository,
    private val settingRepository: ISettingRepository,

    ) : ViewModel() {

    val converter = Converter()


    private val defaultInstruction = InstructionUiState(
        examId = examId,
        title = null,
        content = listOf(ItemUiState(isEditMode = true)).toImmutableList(),
    )
    private val _instructionUiState = mutableStateOf(
        defaultInstruction,
    )
    val instructionUiState: State<InstructionUiState> = _instructionUiState
    val instructions = mutableStateOf(emptyList<InstructionUiState>().toImmutableList())


    private val _instruInputUiState = mutableStateOf(InstruInputUiState("", false))
    val instruInputUiState: State<InstruInputUiState> = _instruInputUiState


    init {

        viewModelScope.launch {
            settingRepository.instructions
                .first()
                .get(examId)
                ?.let {
                    log(it.toString())
                    val uiState = it.toInstructionUiState()
                    _instructionUiState.value =
                        uiState.copy(
                            content = uiState.content.map { it.copy(isEditMode = true) }
                                .toImmutableList(),
                        )
                }
            snapshotFlow { instructionUiState.value }
                .distinctUntilChanged()
                .collectLatest {
                    if (it == defaultInstruction) {
                        val inst = settingRepository.instructions
                            .first()
                            .toMutableMap()
                        inst.remove(examId)
                        settingRepository.setCurrentInstruction(inst)
                    } else {
                        log("save $it")
                        val inst = settingRepository.instructions
                            .first()
                            .toMutableMap()
                        inst[examId] = it.toInstruction()
                        settingRepository.setCurrentInstruction(inst)
                    }
                }
        }

        viewModelScope.launch {
            instructionRepository
                .getAllByExamId(examId)
                .map { instructionList ->
                    instructionList.map {
                        it.toInstructionUiState()
                    }.toImmutableList()
                }
                .collectLatest {
                    instructions.value = it

                }
        }

    }



    // instruction logic

    fun instructionTitleChange(text: String) {
        _instructionUiState.value =
            instructionUiState.value.copy(title = text.ifBlank { null })
    }

    fun onAddInstruction() {
        viewModelScope.launch {
            instructionRepository.upsert(
                instructionUiState.value.toInstruction(),
            )

            _instructionUiState.value = defaultInstruction
        }
    }

    fun addUpInstruction(index: Int) {
        editContentInstruction() {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUiState(isEditMode = true))
            i
        }
    }

    fun addDownInstruction(index: Int) {
        editContentInstruction() {
            it.add(index + 1, ItemUiState(isEditMode = true))

            index + 1
        }
    }

    fun moveUpInstruction(index: Int) {
        if (index == 0) {
            return
        }
        editContentInstruction() {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

            null
        }
    }

    fun moveDownInstruction(index: Int) {
        if (index == instructionUiState.value.content.lastIndex) {
            return
        }
        editContentInstruction() {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }

            null
        }
    }

    fun editInstruction(index: Int) {
        editContentInstruction() {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)
            if (!item.isEditMode) index else null
        }
    }

    fun deleteInstruction(index: Int) {
        if (instructionUiState.value.content.size == 1) {
            return
        }
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content, examId).deleteOnExit()
            }
            it.removeAt(index)
            null
        }
    }

    fun changeTypeInstruction(index: Int, type: Type) {
        editContentInstruction() {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content, examId).deleteOnExit()
            }
            it[index] = ItemUiState(isEditMode = true, type = type)
            index
        }
    }

    fun onTextChangeInstruction(index: Int, text: String) {
        editContentInstruction {
            val item = it[index]
            if (item.type == Type.IMAGE) {
                val name = SvgObject
                    .saveImage(
                        item.content,
                        text,
                        examId,
                    )
                log("name $name")
                it[index] = item.copy(content = name)
            } else {
                it[index] = item.copy(content = text)
            }

            null
        }
    }

    private fun editContentInstruction(
        onItems: suspend (MutableList<ItemUiState>) -> Int?,
    ) {
        viewModelScope.launch {
            var items = instructionUiState.value.content.toMutableList()
            val i = onItems(items)
            if (i != null) {
                items = items.mapIndexed { index, itemUi ->
                    itemUi.copy(focus = index == i)
                }.toMutableList()
            }
            _instructionUiState.value = instructionUiState
                .value
                .copy(
                    content = items.toImmutableList(),
                )
        }
    }

    fun onDeleteInstruction(id: Long) {
        viewModelScope.launch {
            instructionRepository.delete(id)
        }
    }

    fun onUpdateInstruction(id: Long) {
        val oldInstructionUiState = _instructionUiState.value
        if (oldInstructionUiState.id < 0) {
            oldInstructionUiState.content
                .filter { it.type == Type.IMAGE }
                .forEach {
                    getGeneralDir(it.content, examId).delete()
                }
        }
        instructions.value.find { it.id == id }?.let { uiState ->
            _instructionUiState.value = uiState.copy(
                content = uiState.content
                    .map {
                        it.copy(isEditMode = true)
                    }
                    .toImmutableList(),
            )
        }
    }



    fun onInstuInputChanged(text: String) {
        _instruInputUiState.value = instruInputUiState.value.copy(content = text)
    }

    fun onAddInstruTopicFromInput() {
        viewModelScope.launch {
            try {
                val list =
                    converter.textToInstruction(
                        path = instruInputUiState.value.content,
                        examId = examId,
                    )

                log(list.joinToString())
                launch { instructionRepository.insertAll(list) }
                _instruInputUiState.value =
                    instruInputUiState.value.copy(content = "", isError = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _instruInputUiState.value = instruInputUiState.value.copy(isError = true)
            }
        }
    }

    private fun log(msg: String) {
//        co.touchlab.kermit.Logger.e(msg)
    }

}