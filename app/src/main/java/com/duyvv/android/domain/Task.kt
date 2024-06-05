package com.duyvv.android.domain

import java.io.Serializable
import java.util.Calendar

data class Task(
    val id: Int? = null,
    val title: String,
    val description: String,
    val time: Calendar,
    var status: TaskStatus,
    var priority: TaskPriority
) : Serializable

enum class TaskStatus(val des: String) {
    COMPLETED("COMPLETED"),
    UNCOMPLETED("UNCOMPLETED")
}

enum class TaskPriority(val level: Int, val des: String) {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High")
}
