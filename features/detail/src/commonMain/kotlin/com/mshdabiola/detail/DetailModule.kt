package com.mshdabiola.detail

import com.mshdabiola.detail.instruction.InstructionViewModel
import com.mshdabiola.detail.question.QuestionViewModel
import com.mshdabiola.detail.topic.TopicViewModel
import com.mshdabiola.mvvn.commonViewModel
import org.koin.dsl.module

val detailModule = module {
    commonViewModel { param ->
        QuestionViewModel(param[0], param[1], get(), get(), get(), get(), get())
    }
    commonViewModel { param ->
        TopicViewModel(param[0],  get())
    }
    commonViewModel { param ->
        InstructionViewModel(param[0],  get(), get())
    }

}
