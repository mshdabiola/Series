package com.mshdabiola.database.model.session

import androidx.room.Entity

@Entity(tableName = "session_examination")
data class SessionExamination(
    val id: Long,
    val duration: Long,
    val remainingTime: Long,
    val totalMarks: Long,
    val isCompleted: Boolean,
    val progress: Long,
)
