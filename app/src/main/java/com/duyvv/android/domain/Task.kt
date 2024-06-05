package com.duyvv.android.domain

import java.io.Serializable
import java.util.Calendar

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val time: Calendar,
    val status: TaskStatus
) : Serializable

enum class TaskStatus(val des: String) {
    COMPLETED("COMPLETED"),
    UNCOMPLETED("UNCOMPLETED")
}
