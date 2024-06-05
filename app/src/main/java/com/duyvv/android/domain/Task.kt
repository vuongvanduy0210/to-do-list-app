package com.duyvv.android.domain

import java.util.Calendar

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val time: Calendar,
    val status: TaskStatus
)

enum class TaskStatus(val des: String) {
    COMPLETED("COMPLETED"),
    UPCOMING("UPCOMING")
}
