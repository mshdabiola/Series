package com.mshdabiola.series.feature.exam

import androidx.compose.runtime.mutableStateOf
import com.mshdabiola.model.Item
import com.mshdabiola.series.ViewModel
import com.mshdabiola.series.feature.exam.state.ItemUi
import com.mshdabiola.series.feature.exam.state.OptionsUiState
import com.mshdabiola.series.feature.exam.state.QuestionUiState
import kotlinx.collections.immutable.toImmutableList
import java.awt.event.ItemEvent

class ExamViewModel(private val id: Long) : ViewModel() {


    var question =
        mutableStateOf(
            QuestionUiState(
                id = 1,
                content = listOf(
                    ItemUi.Text("", true),
                ).toImmutableList(),
                options = listOf(
                    OptionsUiState(
                        id = 1,
                        content = listOf(ItemUi.Text("",true)).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 2,
                        content = listOf(
                            ItemUi.Text("",true)
                        ).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 3, content = listOf(ItemUi.Text("",true)).toImmutableList(),
                        isAnswer = false
                    ),
                    OptionsUiState(
                        id = 4,
                        content = listOf(
                            ItemUi.Text("",true)
                        ).toImmutableList(),
                        isAnswer = false
                    )
                ).toImmutableList(),
                editMode = true
            )
        )

    val questions= listOf(
        QuestionUiState(
            id = 1,
            content = listOf(
                ItemUi.Text("abiola", true),
                ItemUi.Equation("abiola")
            ).toImmutableList(),
            options = listOf(
                OptionsUiState(
                    id = 1,
                    content = listOf(ItemUi.Text("option1")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 2,
                    content = listOf(
                        ItemUi.Text("option2"),
                        ItemUi.Equation("option2")
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 3, content = listOf(ItemUi.Text("option3")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 4,
                    content = listOf(
                        ItemUi.Text("option4"),
                        ItemUi.Equation("option4")
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
            editMode = false
        ),
        QuestionUiState(
            id = 1,
            content = listOf(
                ItemUi.Text("abiola", true),
                ItemUi.Equation("abiola")
            ).toImmutableList(),
            options = listOf(
                OptionsUiState(
                    id = 1,
                    content = listOf(ItemUi.Text("option1")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 2,
                    content = listOf(
                        ItemUi.Text("option2"),
                        ItemUi.Equation("option2")
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 3, content = listOf(ItemUi.Text("option3")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 4,
                    content = listOf(
                        ItemUi.Text("option4"),
                        ItemUi.Equation("option4")
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
            editMode = false
        ),
        QuestionUiState(
            id = 1,
            content = listOf(
                ItemUi.Text("abiola", true),
                ItemUi.Equation("abiola")
            ).toImmutableList(),
            options = listOf(
                OptionsUiState(
                    id = 1,
                    content = listOf(ItemUi.Text("option1")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 2,
                    content = listOf(
                        ItemUi.Text("option2"),
                        ItemUi.Equation("option2")
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 3, content = listOf(ItemUi.Text("option3")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 4,
                    content = listOf(
                        ItemUi.Text("option4"),
                        ItemUi.Equation("option4")
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
            editMode = false
        ),
        QuestionUiState(
            id = 1,
            content = listOf(
                ItemUi.Text("abiola", true),
                ItemUi.Equation("abiola")
            ).toImmutableList(),
            options = listOf(
                OptionsUiState(
                    id = 1,
                    content = listOf(ItemUi.Text("option1")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 2,
                    content = listOf(
                        ItemUi.Text("option2"),
                        ItemUi.Equation("option2")
                    ).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 3, content = listOf(ItemUi.Text("option3")).toImmutableList(),
                    isAnswer = false
                ),
                OptionsUiState(
                    id = 4,
                    content = listOf(
                        ItemUi.Text("option4"),
                        ItemUi.Equation("option4")
                    ).toImmutableList(),
                    isAnswer = false
                )
            ).toImmutableList(),
            editMode = false
        )
    )

    init {
        println("id is $id")
    }


    fun addUP(questionIndex: Int, index: Int) {
       editContent(questionIndex,index){
           val i=if (index==0)0 else index-1
           it.add(i,ItemUi.Equation("top $i"))
       }
    }
    fun addDown(questionIndex: Int, index: Int) {
        editContent(questionIndex,index){

            it.add(index+1,ItemUi.Equation("bottom ${index+1}"))
        }
    }
    fun moveUP(questionIndex: Int, index: Int) {
        if(index==0)
            return
        editContent(questionIndex,index){

            val upIndex=index-1
            val up=it[upIndex]
            it[upIndex] = it[index]
            it[index] = up

        }
    }
    fun moveDown(questionIndex: Int, index: Int) {

        editContent(questionIndex,index){
            if(index!=it.lastIndex) {
                val upIndex = index + 1
                val up = it[upIndex]
                it[upIndex] = it[index]
                it[index] = up
            }

        }
    }
    fun edit(questionIndex: Int, index: Int) {

        editContent(questionIndex,index){
            var item=it[index]

            item=when(item){
                is ItemUi.Text->item.copy(isEditMode = !item.isEditMode)
                is ItemUi.Equation->item.copy(isEditMode = !item.isEditMode)
                is ItemUi.Image->item.copy(isEditMode = !item.isEditMode)
            }
            it[index]=item
        }
    }
    fun delete(questionIndex: Int, index: Int) {

        editContent(questionIndex,index){
            it.removeAt(index)
        }
    }

    fun changeType(questionIndex: Int, index: Int, type: Int) {
        editContent(questionIndex,index){
            var item=it[index]
            item=when(type){
                0-> ItemUi.Text("text",true)

                1->ItemUi.Equation("equation",true)
                else->ItemUi.Image("image",true)
            }
            it[index]=item
        }
    }
    fun onTextChange(questionIndex: Int, index: Int,text:String) {
        editContent(questionIndex,index){
            var item=it[index]
            item=when(item){
                is ItemUi.Text->item.copy(tex = text)
                is ItemUi.Equation->item.copy(tex = text)
                is ItemUi.Image->item.copy(imageName = text)
            }
            it[index]=item
        }
    }

    private fun editContent(
        questionIndex: Int, index: Int,
        items:(MutableList<ItemUi>)->Unit){
        println("question $questionIndex index $index")
        var quest=question.value

        if (questionIndex==-1){
            val qItem=quest.content.toMutableList()
            items(qItem)
            quest=quest.copy(content = qItem.toImmutableList())

        }else{
            val options=quest.options.toMutableList()
            var option=options[questionIndex]
            val qItem=option.content.toMutableList()
            items(qItem)
            option=option.copy(content = qItem.toImmutableList())
            options[questionIndex]=option

            quest=quest.copy(options=options.toImmutableList())


        }
        question.value=quest
    }

}