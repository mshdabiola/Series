package com.mshdabiola.ui

import com.mshdabiola.model.data.Exam
import com.mshdabiola.model.data.ExamWithSub

data class ExamUiDesktop(
    val id: Long = -1,
    val subjectID: Long,
    val year: Long,
    val subject: String,
    val isObjOnly: Boolean,
    val isSelected :Boolean=false
)


fun ExamWithSub.toUiDesktop() = ExamUiDesktop(
    id = id,
    subjectID = subjectID,
    year = year,
    subject = subject,
    isObjOnly = isObjOnly
)

fun ExamUiDesktop.toExam() = Exam(id, subjectID, isObjOnly, year)