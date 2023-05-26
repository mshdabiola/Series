package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.data.repository.IQuestionRepository
import com.mshdabiola.model.data.Type
import com.mshdabiola.series.ViewModel
import com.mshdabiola.ui.state.ItemUi
import com.mshdabiola.ui.state.OptionUiState
import com.mshdabiola.ui.state.QuestionUiState
import com.mshdabiola.ui.toQuestionUiState
import com.mshdabiola.ui.toQuestionWithOptions
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class ExamViewModel(
    private val id: Long,
    private val questionRepository: IQuestionRepository
) : ViewModel() {


    private var _question =
        mutableStateOf(
            getEmptyQuestion()
        )
    val question: State<QuestionUiState> = _question

    init {
        viewModelScope.launch {
            questionRepository.getAll()
                .collectLatest {
                    println(it.joinToString())
                }
        }
    }

    val questions = questionRepository.getAllWithExamId(id)
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

    fun onDeleteQuestion(id:Long){
        rearrangeAndSave { questionUiStates ->
           val index= questionUiStates.indexOfFirst { it.id==id }
            questionUiStates.removeAt(index)
            questionRepository.delete(id)
        }
    }

    fun onMoveUpQuestion(id:Long){
        val index=questions.value.indexOfFirst { it.id==id }
        if (index==0)
            return

        rearrangeAndSave {
            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up
        }
    }

    fun onMoveDownQuestion(id:Long){
        val q=questions.value
        val index=q.indexOfFirst { it.id==id }
        if (index==q.lastIndex)
            return

        rearrangeAndSave {
            val downIndex = index + 1
            val down = it[downIndex]
            it[downIndex] = it[index]
            it[index] = down
        }
    }
    fun onUpdateQuestion(id: Long){
        val question=questions
            .value
            .find { it.id==id }

        question?.let {
            _question.value=it
        }
    }


    fun onAddQuestion() {
        var question2 = _question.value


        val number=if (question2.nos==-1L)questions.value.size.toLong() + 1 else question2.nos
        question2 = question2.copy(nos = number)

        viewModelScope.launch {
            println("insert")
            questionRepository.insert(question2.toQuestionWithOptions(examId = id))
        }
        _question.value = getEmptyQuestion()
    }

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
                ItemUi(isEditMode = true)
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

    private fun rearrangeAndSave(onEdit:suspend (MutableList<QuestionUiState>)->Unit){
        viewModelScope.launch {
            var list=questions.value.toMutableList()
            onEdit(list)
            var num=0
            list=list.mapIndexed { index, questionUiState ->
                questionUiState.copy(nos = index+1L)
                }.toMutableList()

            //save
            questionRepository.insertMany(list.map { it.toQuestionWithOptions(id) })
        }

    }


    fun addUP(questionIndex: Int, index: Int) {
        editContent(questionIndex, index) {
            val i = if (index == 0) 0 else index - 1
            it.add(i, ItemUi(isEditMode = true))
        }
    }

    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex, index) {

            it.add(index + 1, ItemUi(isEditMode = true))
        }
    }

    fun moveUP(questionIndex: Int, index: Int) {
        if (index == 0)
            return
        editContent(questionIndex, index) {

            val upIndex = index - 1
            val up = it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

        }
    }

    fun moveDown(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            if (index != it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }

        }
    }

    fun edit(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            val item = it[index]

            it[index] = item.copy(isEditMode = !item.isEditMode)
        }
    }

    fun delete(questionIndex: Int, index: Int) {

        editContent(questionIndex, index) {
            it.removeAt(index)
        }
        removeEmptyOptions()

    }

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

    fun changeType(questionIndex: Int, index: Int, type: Type) {
        editContent(questionIndex, index) {
            var item = it[index]

            it[index] = ItemUi(isEditMode = true, type = type)
        }
    }

    fun onTextChange(questionIndex: Int, index: Int, text: String) {
        editContent(questionIndex, index) {
            val item = it[index]

            it[index] = item.copy(content = text)
        }
    }

    private fun editContent(
        questionIndex: Int, index: Int,
        items: (MutableList<ItemUi>) -> Unit
    ) {
        var quest = _question.value

        if (questionIndex == -1) {
            val qItem = quest.content.toMutableList()
            items(qItem)
            quest = quest.copy(content = qItem.toImmutableList())

        } else {
            val options = quest.options.toMutableList()
            var option = options[questionIndex]
            val qItem = option.content.toMutableList()
            items(qItem)
            option = option.copy(content = qItem.toImmutableList())
            options[questionIndex] = option

            quest = quest.copy(options = options.toImmutableList())


        }
        _question.value = quest

    }


}