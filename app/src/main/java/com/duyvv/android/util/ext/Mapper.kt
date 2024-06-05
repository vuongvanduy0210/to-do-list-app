package com.duyvv.android.util.ext

import com.duyvv.android.database.entity.TaskEntity
import com.duyvv.android.domain.Task
import com.duyvv.android.domain.TaskStatus
import com.duyvv.android.util.app.FormatUtils

fun Task.toTaskEntity() = TaskEntity(
    title = title,
    description = description,
    time = FormatUtils.convertCalendarToString(time),
    isCompleted = status == TaskStatus.COMPLETED
)

fun TaskEntity.toDomainModel(): Task {
    val time = FormatUtils.convertStringToCalendar(time)
    val status = if (isCompleted) {
        TaskStatus.COMPLETED
    } else {
        TaskStatus.UNCOMPLETED
    }
    return Task(id, title, description, time, status)
}
