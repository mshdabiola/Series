package com.mshdabiola.data.repository.inter

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.Instruction
import com.mshdabiola.model.data.QuestionFull
import com.mshdabiola.model.data.Subject
import com.mshdabiola.model.data.Topic
import kotlinx.coroutines.CoroutineScope

interface IExInPortRepository {

    suspend fun export(
        coroutineScope: CoroutineScope,
        subjectId: Long,
        onFinish: (List<Subject>, List<Exam>, List<QuestionFull>, List<Instruction>, List<Topic>) -> Unit
    )
}