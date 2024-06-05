package com.duyvv.android.util.ext

import com.duyvv.android.database.entity.TaskEntity
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskPriority
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.util.app.FormatUtils

fun Task.toTaskEntity() = TaskEntity(
    title = title,
    description = description,
    time = FormatUtils.convertCalendarToString(time),
    isCompleted = status == TaskStatus.COMPLETED,
    priority = priority.level
)

fun TaskEntity.toDomainModel(): Task {
    val time = FormatUtils.convertStringToCalendar(time)
    val status = if (isCompleted) {
        TaskStatus.COMPLETED
    } else {
        TaskStatus.UNCOMPLETED
    }
    val prior = when (priority) {
        1 -> TaskPriority.LOW
        2 -> TaskPriority.MEDIUM
        3 -> TaskPriority.HIGH
        else -> TaskPriority.LOW
    }
    return Task(id, title, description, time, status, prior)
}
