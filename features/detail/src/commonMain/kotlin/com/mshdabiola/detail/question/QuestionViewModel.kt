package com.mshdabiola.detail.question

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshotFlow
import com.mshdabiola.data.Converter
import com.mshdabiola.data.SvgObject
import com.mshdabiola.data.repository.IExaminationRepository
import com.mshdabiola.data.repository.IInstructionRepository
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.data.repository.ISettingRepository
import com.mshdabiola.data.repository.ITopicRepository
import com.mshdabiola.model.ImageUtil.getGeneralDir
import com.mshdabiola.model.data.Type
import com.mshdabiola.mvvn.ViewModel
import com.mshdabiola.ui.state.ExamInputUiState
import com.mshdabiola.ui.state.InstruInputUiState
import com.mshdabiola.ui.state.ItemUiState
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.state.TopicInputUiState
import com.mshdabiola.ui.toInstructionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import com.mshdabiola.ui.toUi
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class QuestionViewModel (
    private val examId: Long,
    private val subjectId: Long,
    private val questionRepository: IQuestionRepository,
    private val instructionRepository: IInstructionRepository,
    private val examRepository: IExaminationRepository,
    private val settingRepository: ISettingRepository,
    private val topicRepository: ITopicRepository,

    ): ViewModel() {

    val converter = Converter()

    private var _question =
        mutableStateOf(
            getEmptyQuestion(),
        )
    val question: State<QuestionUiState> = _question
    private val _instructIdError = mutableStateOf(false)
    val instructIdError: State<Boolean> = _instructIdError

    val questions = mutableStateOf(emptyList<QuestionUiState>().toImmutableList())


    init {

        viewModelScope.launch {
            settingRepository.questions
                .first()[examId]
                ?.let {
                    log(it.toString())
                    val uiState = it.toQuestionUiState(isEdit = true)
                    _question.value =
                        uiState
                }
            snapshotFlow { question.value }
                .distinctUntilChanged()
                .collectLatest {

                    if (it == getEmptyQuestion()) {
                        val inst = settingRepository.questions
                            .first()
                            .toMutableMap()
                        inst.remove(examId)
                        settingRepository.setCurrentQuestion(inst)
                        log("remove")
                        //  settingRepository.removeQuestion(examId)
                    } else {
                        val inst = settingRepository.questions
                            .first()
                            .toMutableMap()
                        inst[examId] = it.toQuestionWithOptions(examId)
                        settingRepository.setCurrentQuestion(inst)
                        // settingRepository.setCurrentQuestion(it.toQuestionWithOptions(examId))
                    }
                }
        }

        viewModelScope.launch() {
            questionRepository.getByExamId(examId)
                .map {
                    it
                        .map { it.toQuestionUiState() }
                        .sortedBy { it.isTheory }
                        .toImmutableList()
                }
                .collectLatest {

                    questions.value = it

                }
        }



    }

    private fun updateExamType(isObjOnly: Boolean) {
        viewModelScope.launch {
            val exam = examRepository.getOne(examId).firstOrNull() ?: return@launch

            if (exam.isObjectiveOnly != isObjOnly) {
                examRepository.upsert(exam.copy(isObjectiveOnly = isObjOnly))
            }
        }
    }

    // question logic
    fun onDeleteQuestion(id: Long) {
        rearrangeAndSave { questionUiStates ->
            val index = questionUiStates.indexOfFirst { it.id == id }
            questionUiStates.removeAt(index)
            questionRepository.delete(id)
        }
    }

    fun onMoveUpQuestion(id: Long) {
        val index = questions.value.indexOfFirst { it.id == id }
        if (index == 0) {
            return
        }

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
        if (index == q.lastIndex) {
            return
        }

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
        val oldQuetions = _question.value
        if (oldQuetions.id < 0) {
            oldQuetions.contents
                .filter { it.type == Type.IMAGE }
                .forEach {
                    getGeneralDir(it.content, examId).delete()
                }
            oldQuetions.options
                ?.flatMap { it.content }
                ?.filter { it.type == Type.IMAGE }
                ?.forEach {
                    getGeneralDir(it.content, examId).delete()
                }
        }
        question?.let {
            _question.value = it
        }
    }

    fun onAddQuestion() {
        var question2 = _question.value

        val number =
            if (question2.number == -1L) questions.value.filter { it.isTheory == question2.isTheory }.size.toLong() + 1 else question2.number
        question2 = question2.copy(number = number)

        viewModelScope.launch {
            val allIsObj = questions.value.all { it.isTheory.not() } && question2.isTheory.not()
            questionRepository.upsert(question2.toQuestionWithOptions(examId = examId))
            updateExamType(isObjOnly = allIsObj)
        }
        _question.value = getEmptyQuestion(question2.options!!.size, question2.isTheory)
    }

    var answerJob: Job? = null
    fun onAnswerClick(questionId: Long, optionId: Long) {
        if (answerJob != null) {
            return
        }
        answerJob = viewModelScope.launch {
            val questionUiStates = questions.value
            val questionIndex = questionUiStates.indexOfFirst { it.id == questionId }
            var question = questionUiStates[questionIndex]
            var options = question
                .options
                ?.map {
                    it.copy(isAnswer = it.id == optionId)
                }

            question = question.copy(
                options = options?.toImmutableList(),
            )
            questionRepository.upsert(question.toQuestionWithOptions(examId))
            answerJob = null
        }
    }

    // question edit logic
    fun addOption() {
        var question = _question.value
        question = question.copy(
            options = question.options
                ?.toMutableList()
                ?.apply {
                    add(
                        OptionUiState(
                            nos = (question.options!!.size + 1).toLong(),
                            content = listOf(
                                ItemUiState(isEditMode = true, focus = true),
                            ).toImmutableList(),
                            isAnswer = false,
                        ),
                    )
                }
                ?.toImmutableList(),

            )

        _question.value = question
    }

    fun onAddAnswer(show: Boolean) {
        var question = _question.value
        question = question.copy(
            answers = if (show) {
                listOf(
                    ItemUiState(isEditMode = true, focus = true),
                ).toImmutableList()
            } else {
                null
            },
        )

        _question.value = question
    }

    fun isTheory(isT: Boolean) {
        var question = _question.value

        question = if (isT) {
            question.copy(
                answers = listOf(
                    ItemUiState(isEditMode = true, focus = false),
                ).toImmutableList(),
                options = emptyList<OptionUiState>().toImmutableList(),
            )
        } else {
            getEmptyQuestion()
        }
        _question.value = question.copy(isTheory = isT)
    }

    private fun getEmptyQuestion(optionNo: Int = 4, isTheory: Boolean = false): QuestionUiState {
        val opNumb = if (isTheory) 0 else optionNo
        return QuestionUiState(
            number = -1,
            examId = examId,
            contents = listOf(
                ItemUiState(isEditMode = true, focus = true),
            ).toImmutableList(),
            options = (1..opNumb)
                .map {
                    OptionUiState(
                        nos = it.toLong(),
                        content = listOf(
                            ItemUiState(isEditMode = true),
                        ).toImmutableList(),
                        isAnswer = false,
                    )
                }.toImmutableList(),
            isTheory = isTheory,
            answers = if (isTheory) {
                listOf(
                    ItemUiState(isEditMode = true),
                ).toImmutableList()
            } else {
                null
            },
        )
    }

    private var job: Job? = null
    private fun rearrangeAndSave(onEdit: suspend (MutableList<QuestionUiState>) -> Unit) {
        if (job != null) {
            return
        }

        job = viewModelScope.launch {
            var list = questions.value.toMutableList()
            onEdit(list)
            var theory = 0L
            var obj = 0L
            list = list.map { questionUiState ->
                if (questionUiState.isTheory) {
                    theory += 1
                    questionUiState.copy(number = theory)
                } else {
                    obj += 1
                    questionUiState.copy(number = obj)
                }
            }.toMutableList()

            // save
            questionRepository.upsertMany(list.map { it.toQuestionWithOptions(examId) })
            job = null
        }
    }

    fun addUP(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUiState(isEditMode = true))
            i
        }
    }

    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex) {
            it.add(index + 1, ItemUiState(isEditMode = true))
            index + 1
        }
    }

    fun moveUP(questionIndex: Int, index: Int) {
        if (index == 0) {
            return
        }
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
        var removeOption = false
        editContent(questionIndex) {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content, examId).deleteOnExit()

//                FileManager.delete(
//                    oldItem.content,
//                    subjectId,
//                    examId,
//                    FileManager.ImageType.QUESTION
//                )
            }

            if (questionIndex < 0) {
                if (it.size == 1) {
                    it[index] = ItemUiState(isEditMode = true, focus = true)
                } else {
                    it.removeAt(index)
                }
            } else {
                it.removeAt(index)
                if (it.isEmpty()) {
                    removeOption = true
                }
            }
            null
        }
        if (removeOption) {
            val quest = _question.value
            val options = quest.options?.toMutableList()
            val option = options?.removeAt(questionIndex)

            if (option?.id!! > 0) {
                viewModelScope.launch {
                    questionRepository.deleteOption(option.id)
                }
            }
            _question.value = quest.copy(options = options.toImmutableList())
        }
    }

    fun changeType(questionIndex: Int, index: Int, type: Type) {
        editContent(questionIndex) {
            val oldItem = it[index]
            if (oldItem.type == Type.IMAGE) {
                getGeneralDir(oldItem.content, examId).deleteOnExit()
            }
            it[index] = ItemUiState(isEditMode = true, type = type)
            index
        }
    }

    // focus returen value
    fun onTextChange(questionIndex: Int, index: Int, text: String) {
        editContent(questionIndex) {
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



    private fun editContent(
        questionIndex: Int,
        items: suspend (MutableList<ItemUiState>) -> Int?,
    ) {
        viewModelScope.launch {
            var quest = _question.value

            when (questionIndex) {
                -1 -> {
                    var qItem = quest.contents.toMutableList()
                    val i = items(qItem)
                    if (i != null) {
                        qItem = qItem.mapIndexed { index, itemUi ->
                            itemUi.copy(focus = index == i)
                        }.toMutableList()
                    }

                    quest = quest.copy(contents = qItem.toImmutableList())
                }

                -2 -> {
                    var qItem = quest.answers?.toMutableList()
                    if (qItem != null) {
                        val i = items(qItem)
                        if (i != null) {
                            qItem = qItem.mapIndexed { index, itemUi ->
                                itemUi.copy(focus = index == i)
                            }.toMutableList()
                        }

                        quest = quest.copy(answers = qItem?.toImmutableList())
                    }
                }

                else -> {
                    val options = quest.options!!.toMutableList()
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
            }

            _question.value = quest
        }
    }



    private val _examInputUiState = mutableStateOf(ExamInputUiState("", false))
    val examInputUiState: State<ExamInputUiState> = _examInputUiState

    fun onExamInputChanged(text: String) {
        _examInputUiState.value = examInputUiState.value.copy(content = text)
    }

    fun onAddExamFromInput() {
        val count = questions.value.partition { it.isTheory.not() }

        viewModelScope.launch {
            try {
                val list =
                    converter.textToQuestion(
                        path = examInputUiState.value.content,
                        examId = examId,
                        nextObjNumber = count.first.size + 1L,
                        nextTheoryNumber = count.second.size + 1L,
                    )

                log(list.joinToString())
                launch { questionRepository.upsertMany(list) }
                _examInputUiState.value = examInputUiState.value.copy(content = "", isError = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _examInputUiState.value = examInputUiState.value.copy(isError = true)
            }
        }
    }

    private val _topicInputUiState = mutableStateOf(TopicInputUiState("", false))
    val topicInputUiState: State<TopicInputUiState> = _topicInputUiState

    fun onTopicInputChanged(text: String) {
        _topicInputUiState.value = topicInputUiState.value.copy(content = text)
    }


    fun onAddTopicFromInput() {
        viewModelScope.launch {
            try {
                val list =
                    converter.textToTopic(
                        path = topicInputUiState.value.content,
                        subjectId = subjectId,
                    )

                log(list.joinToString())
                launch { topicRepository.insertAll(list) }
                _topicInputUiState.value =
                    topicInputUiState.value.copy(content = "", isError = false)
            } catch (e: Exception) {
                e.printStackTrace()
                _topicInputUiState.value = topicInputUiState.value.copy(isError = true)
            }
        }
    }

    private val _instruInputUiState = mutableStateOf(InstruInputUiState("", false))
    val instruInputUiState: State<InstruInputUiState> = _instruInputUiState

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

    fun onInstructionIdChange(text: String) {
//        try {
//            if (text.isBlank()) {
//                _instructIdError.value = false
//                _question.value = question.value.copy(instructionUiState = null)
//            } else {
//                val instr = instructions.value.find { it.id == text.toLong() }
//                _instructIdError.value = instr == null
//
//                _question.value = question.value.copy(instructionUiState = instr)
//            }
//        } catch (e: Exception) {
//            _instructIdError.value = true
//        }
    }

    fun onTopicSelect(id: Long) {
//        val topic = topicUiStates.value.find { it.id == id }
//        _question.value = question.value.copy(topicUiState = topic)
    }



    private fun log(msg: String) {
//        co.touchlab.kermit.Logger.e(msg)
    }
}