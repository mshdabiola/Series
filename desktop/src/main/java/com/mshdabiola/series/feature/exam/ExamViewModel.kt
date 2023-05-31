package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.repository.inter.IInstructionRepository
import com.mshdabiola.data.repository.inter.IQuestionRepository
import com.mshdabiola.data.repository.inter.ITopicRepository
import com.mshdabiola.model.data.Type
import com.mshdabiola.series.ViewModel
import com.mshdabiola.ui.state.InstructionUiState
import com.mshdabiola.ui.state.ItemUi
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicUiState
import com.mshdabiola.ui.toInstruction
import com.mshdabiola.ui.toInstructionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import com.mshdabiola.ui.toTopic
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExamViewModel(
    private val examId: Long,
    private val subjectId: Long,
    private val questionRepository: IQuestionRepository,
    private val instructionRepository: IInstructionRepository,
    private val topicRepository: ITopicRepository
) : ViewModel() {


    private var _question =
        mutableStateOf(
            getEmptyQuestion()
        )
    val question: State<QuestionUiState> = _question
    private val _instructIdError = mutableStateOf(false)
    val instructIdError: State<Boolean> = _instructIdError

    val questions = questionRepository.getAllWithExamId(examId)
        .map {
            it
                .map { it.toQuestionUiState() }
                .toImmutableList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList<QuestionUiState>().toImmutableList()
        )

    //instruction
    val instructions = instructionRepository
        .getAll(examId)
        .map { instructionList ->
            instructionList.map {
                it.toInstructionUiState()
            }.toImmutableList()
        }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList<InstructionUiState>().toImmutableList()
        )

    private val _instructionUiState = mutableStateOf(
        InstructionUiState(
            examId = examId,
            title = null,
            content = listOf(ItemUi(isEditMode = true)).toImmutableList()
        )
    )
    val instructionUiState: State<InstructionUiState> = _instructionUiState

    //topic

    val topicUiStates = topicRepository
        .getAllBySubject(subjectId)
        .map { it.map { it.toUi() }.toImmutableList() }
        .stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList<TopicUiState>().toImmutableList()
        )
    private val _topicUiState = mutableStateOf(TopicUiState(subjectId = subjectId, name = ""))
    val topicUiState: State<TopicUiState> = _topicUiState


    //question logic
    fun onDeleteQuestion(id: Long) {
        rearrangeAndSave { questionUiStates ->
            val index = questionUiStates.indexOfFirst { it.id == id }
            questionUiStates.removeAt(index)
            questionRepository.delete(id)
        }
    }

    fun onMoveUpQuestion(id: Long) {
        val index = questions.value.indexOfFirst { it.id == id }
        if (index == 0)
            return

        rearrangeAndSave {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up
        }
    }

    fun onMoveDownQuestion(id: Long) {
        val q = questions.value
        val index = q.indexOfFirst { it.id == id }
        if (index == q.lastIndex)
            return

        rearrangeAndSave {
            val downIndex = index + 1
            val down = it[downIndex]
            it[downIndex] = it[index]
            it[index] = down
        }
    }

    fun onUpdateQuestion(id: Long) {
        val question = questions
            .value
            .find { it.id == id }

        question?.let {
            _question.value = it
        }
    }

    fun onAddQuestion() {
        var question2 = _question.value


        val number = if (question2.nos == -1L) questions.value.size.toLong() + 1 else question2.nos
        question2 = question2.copy(nos = number)

        viewModelScope.launch {
            println("insert")
            questionRepository.insert(question2.toQuestionWithOptions(examId = examId))
        }
        _question.value = getEmptyQuestion()
    }

    fun onAnswerClick(questionId: Long, optionId: Long) {

        rearrangeAndSave { questionUiStates ->
            val questionIndex = questionUiStates.indexOfFirst { it.id == questionId }
            var question = questionUiStates[questionIndex]
            var options = question
                .options
                .map {
                    it.copy(isAnswer = it.id == optionId)
                }

            question = question.copy(
                options = options.toImmutableList()
            )
            questionUiStates[questionIndex] = question
        }

    }


    //question edit logic
    fun addOption() {
        var question = _question.value
        question = question.copy(
            options = question.options
                .toMutableList()
                .apply {
                    add(
                        OptionUiState(
                            nos = (question.options.size + 1).toLong(),
                            content = listOf(
                                ItemUi(isEditMode = true)
                            ).toImmutableList(),
                            isAnswer = false
                        )
                    )
                }
                .toImmutableList()

        )

        _question.value = question
    }

    private fun getEmptyQuestion(): QuestionUiState {
        return QuestionUiState(
            nos = -1,
            content = listOf(
                ItemUi(isEditMode = true, focus = true)
            ).toImmutableList(),
            options = listOf(
                OptionUiState(
                    nos = 1,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionUiState(
                    nos = 2,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionUiState(
                    nos = 3, content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionUiState(
                    nos = 4,
                    content = listOf(
                        ItemUi(isEditMode = true)
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
        )
    }

    private fun rearrangeAndSave(onEdit: suspend (MutableList<QuestionUiState>) -> Unit) {
        viewModelScope.launch {
            var list = questions.value.toMutableList()
            onEdit(list)
            var num = 0
            list = list.mapIndexed { index, questionUiState ->
                questionUiState.copy(nos = index + 1L)
            }.toMutableList()

            //save
            questionRepository.insertMany(list.map { it.toQuestionWithOptions(examId) })
        }

    }


    fun addUP(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUi(isEditMode = true))
            i
        }
    }

    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex) {

            it.add(index + 1, ItemUi(isEditMode = true))
            index + 1
        }
    }

    fun moveUP(questionIndex: Int, index: Int) {
        if (index == 0)
            return
        editContent(questionIndex) {

            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

            null
        }
    }

    fun moveDown(questionIndex: Int, index: Int) {

        editContent(questionIndex) {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }
            null

        }
    }

    fun edit(questionIndex: Int, index: Int) {

        editContent(questionIndex) {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)

            if (it[index].isEditMode) index else null
        }
    }

    fun delete(questionIndex: Int, index: Int) {

        editContent(questionIndex) {
            it.removeAt(index)
            null
        }
        removeEmptyOptions()

    }


    fun changeType(questionIndex: Int, index: Int, type: Type) {
        editContent(questionIndex) {
            var item = it[index]

            it[index] = ItemUi(isEditMode = true, type = type)
            index
        }
    }

    fun onTextChange(questionIndex: Int, index: Int, text: String) {
        editContent(questionIndex) {
            val item = it[index]

            it[index] = item.copy(content = text)
            null
        }
    }

    fun onTopicSelect(id: Long) {
        _question.value = question.value.copy(topicId = id)
    }

    private fun editContent(
        questionIndex: Int, items: (MutableList<ItemUi>) -> Int?
    ) {
        var quest = _question.value

        if (questionIndex == -1) {
            var qItem = quest.content.toMutableList()
            val i = items(qItem)
            if (i != null) {
                qItem = qItem.mapIndexed { index, itemUi ->
                    itemUi.copy(focus = index == i)
                }.toMutableList()
            }

            quest = quest.copy(content = qItem.toImmutableList())

        } else {
            val options = quest.options.toMutableList()
            var option = options[questionIndex]
            var qItem = option.content.toMutableList()
            val i = items(qItem)
            if (i != null) {
                qItem = qItem.mapIndexed { index, itemUi ->
                    itemUi.copy(focus = index == i)
                }.toMutableList()
            }

            option = option.copy(content = qItem.toImmutableList())
            options[questionIndex] = option

            quest = quest.copy(options = options.toImmutableList())


        }
        _question.value = quest

    }

    //remove option when its items is empty
    private fun removeEmptyOptions() {
        val question = question.value
        if (question.options.any { it.content.isEmpty() }) {
            val index = question.options.indexOfFirst { it.content.isEmpty() }
            var options = question.options.toMutableList()
            options.removeAt(index)
            options = options.mapIndexed { index2, optionsUiState ->
                optionsUiState.copy(id = index2.toLong())
            }.toMutableList()
            _question.value = question.copy(options = options.toImmutableList())
        }
    }

    fun onInstructionIdChange(text: String) {
        try {

            if (text.isBlank()) {
                _instructIdError.value = false
                _question.value = question.value.copy(instructionUiState = null)

            } else {
                val instr = instructions.value.find { it.id == text.toLong() }
                _instructIdError.value = instr == null

                _question.value = question.value.copy(instructionUiState = instr)
            }

            println()

        } catch (e: Exception) {
            _instructIdError.value = true

        }
    }


    //instruction logic

    fun instructionTitleChange(text: String) {
        _instructionUiState.value =
            instructionUiState.value.copy(title = text.ifBlank { null })
    }


    fun onAddInstruction() {
        viewModelScope.launch {
            instructionRepository.insert(
                instructionUiState.value.toInstruction()
            )

            _instructionUiState.value = InstructionUiState(
                examId = examId,
                title = null,
                content = listOf(ItemUi(isEditMode = true)).toImmutableList()
            )
        }
    }

    fun addUpInstruction(index: Int) {
        editContentInstruction() {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUi(isEditMode = true))
            i
        }
    }

    fun addDownInstruction(index: Int) {
        editContentInstruction() {

            it.add(index + 1, ItemUi(isEditMode = true))

            index + 1
        }
    }

    fun moveUpInstruction(index: Int) {
        if (index == 0)
            return
        editContentInstruction() {

            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

            null

        }
    }

    fun moveDownInstruction(index: Int) {

        if (index == instructionUiState.value.content.lastIndex)
            return
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

        if (instructionUiState.value.content.size == 1)
            return
        editContentInstruction() {
            it.removeAt(index)
            null
        }

    }


    fun changeTypeInstruction(index: Int, type: Type) {
        editContentInstruction() {

            it[index] = ItemUi(isEditMode = true, type = type)
            index
        }
    }

    fun onTextChangeInstruction(index: Int, text: String) {
        editContentInstruction() {
            val item = it[index]

            it[index] = item.copy(content = text)
            null
        }
    }

    private fun editContentInstruction(
        onItems: (MutableList<ItemUi>) -> Int?
    ) {
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
                content = items.toImmutableList()
            )
    }

    fun onDeleteInstruction(id: Long) {
        viewModelScope.launch {
            instructionRepository.delete(id)
        }

    }

    fun onUpdateInstruction(id: Long) {
        instructions.value.find { it.id == id }?.let { uiState ->
            _instructionUiState.value = uiState.copy(
                content = uiState.content
                    .map {
                        it.copy(isEditMode = true)
                    }
                    .toImmutableList())
        }

    }


    //topic logic

    fun onAddTopic() {
        viewModelScope.launch {
            topicRepository.insert(topicUiState.value.toTopic())
            _topicUiState.value = TopicUiState(subjectId = subjectId, name = "")
        }
    }

    fun onTopicChange(text: String) {
        _topicUiState.value = topicUiState.value.copy(name = text)
    }

    fun onDeleteTopic(id: Long) {
        viewModelScope.launch {
            topicRepository.delete(id)
        }
    }

    fun onUpdateTopic(id: Long) {
        topicUiStates.value.find { it.id == id }?.let {
            _topicUiState.value = it.copy(focus = true)
        }
    }


}